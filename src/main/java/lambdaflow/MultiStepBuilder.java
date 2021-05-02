package lambdaflow;

import java.util.List;
import java.util.function.Function;

public interface MultiStepBuilder<IN, OUT> {
  <B> MultiStepBuilder<IN, B> withMapping(Function<List<IN>, List<B>> f);

  MultiStepBuilder<IN, OUT> withName(String name);

  Step<IN, OUT> build();
}
