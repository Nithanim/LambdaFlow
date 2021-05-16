package lambdaflow.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import lambdaflow.MappingFunction;
import lambdaflow.Step;

/** A simple step implementation that computes the mapping of items in parallel. */
class ParallelSingleStep<IN, OUT> implements Step<IN, OUT> {
  private final ExecutorService pool;
  private final MappingFunction<IN, OUT> mappingFunction;

  public ParallelSingleStep(int numberThreads, MappingFunction<IN, OUT> mappingFunction) {
    this.pool = Executors.newFixedThreadPool(numberThreads);
    this.mappingFunction = mappingFunction;
  }

  /**
   * Splits all items into separate tasks to be computed in parallel. Waits for all tasks to be
   * completed successfully and then returns. If any task throws an exception, an attempt is made to
   * cancel all other tasks by interrupting them and then rethrows the exception. Shutdown of the
   * cancelled tasks is not awaited.
   */
  @Override
  public List<OUT> process(List<IN> input) throws Exception {
    CountDownLatch cdl = new CountDownLatch(input.size());
    var firstExceptionHolder = new AtomicReference<Exception>();

    List<Callable<OUT>> tasks = createTasks(input, cdl, firstExceptionHolder);
    List<Future<OUT>> futures = pool.invokeAll(tasks);

    cdl.await();
    var firstException = firstExceptionHolder.get();
    if (firstException == null) {
      return collectResultsOfFutures(futures);
    } else {
      cancelFutures(futures);
      throw firstException;
    }
  }

  private List<Callable<OUT>> createTasks(
      List<IN> input, CountDownLatch cdl, AtomicReference<Exception> firstExceptionHolder) {
    return input.stream()
        .map(createCallable(cdl, firstExceptionHolder))
        .collect(Collectors.toList());
  }

  private Function<IN, Callable<OUT>> createCallable(
      CountDownLatch cdl, AtomicReference<Exception> firstExceptionHolder) {
    return in ->
        () -> {
          try {
            return mappingFunction.apply(in);
          } catch (Exception ex) {
            // This is a "dirty" workaround for "fail fast", if one future fails to compute
            // The saved "first" exception is the flag for the main thread if a cancellation is
            // desired or everything went successful
            firstExceptionHolder.compareAndSet(null, ex);
            releaseWaitingThreadsOnCountDownLatch(cdl);
            throw ex;
          } finally {
            cdl.countDown();
          }
        };
  }

  private void releaseWaitingThreadsOnCountDownLatch(CountDownLatch cdl) {
    for (int i = 0; i < cdl.getCount(); i++) {
      cdl.countDown();
    }
  }

  private void cancelFutures(List<Future<OUT>> futures) {
    futures.forEach(f -> f.cancel(true));
  }

  private List<OUT> collectResultsOfFutures(List<Future<OUT>> futures)
      throws InterruptedException, ExecutionException {
    List<OUT> results = new ArrayList<>();
    for (Future<OUT> future : futures) {
      results.add(future.get());
    }
    return results;
  }

  @Override
  public void close() {
    pool.shutdownNow();
  }
}
