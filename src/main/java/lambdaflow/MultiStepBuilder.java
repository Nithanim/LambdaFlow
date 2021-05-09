package lambdaflow;

import java.util.List;

public interface MultiStepBuilder<IN, OUT> {
  <B> MultiStepBuilder<IN, B> withMapping(MappingFunction<List<IN>, List<B>> f);

  MultiStepBuilder<IN, OUT> withName(String name);

  Step<IN, OUT> build();
}
