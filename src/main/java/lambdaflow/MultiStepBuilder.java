package lambdaflow;

import java.util.List;
import java.util.function.Function;

/**
 * Builder for a step that takes all items to be processed at once.
 */
public interface MultiStepBuilder<IN, OUT> {
  <B> MultiStepBuilder<IN, B> withMapping(MappingFunction<List<IN>, List<B>> f);

  MultiStepBuilder<IN, OUT> withName(String name);

  MultiStepBuilder<IN, OUT> withGrouping(Function<IN, String> groupingFunction);

  Step<IN, OUT> build();
}
