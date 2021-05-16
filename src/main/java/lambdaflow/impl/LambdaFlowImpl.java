package lambdaflow.impl;

import java.util.Collections;
import java.util.List;
import lambdaflow.Step;
import lambdaflow.errorhandling.FlowErrorStrategy;

public class LambdaFlowImpl<IN, OUT> implements lambdaflow.LambdaFlow<IN, OUT> {
  private final List<Step<?, ?>> steps;
  private final FlowErrorStrategy errorStrategy;

  public LambdaFlowImpl(List<Step<?, ?>> steps, FlowErrorStrategy errorStrategy) {
    this.steps = steps;
    this.errorStrategy = errorStrategy;
  }

  public static <T> LambdaFlowBuilderImpl<T, T> builder(Class<T> dataClass) {
    return new LambdaFlowBuilderImpl<>();
  }

  @SuppressWarnings("unchecked")
  public List<OUT> process(List<IN> input) throws Exception {
    Object intermediate = input;
    for (Step<?, ?> step : steps) {
      intermediate = ((Step<Object, ?>) step).process((List<Object>) intermediate);
    }
    return Collections.unmodifiableList((List<OUT>) intermediate);
  }

  @Override
  public void close() throws Exception {
    Exception firstException = null;
    for (Step<?, ?> step : steps) {
      try {
        step.close();
      } catch (Exception ex) {
        if (firstException == null) {
          firstException = ex;
        }
      }
    }
    if (firstException != null) {
      throw firstException;
    }
  }
}
