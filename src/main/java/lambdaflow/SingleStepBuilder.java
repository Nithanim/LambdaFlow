package lambdaflow;

import lambdaflow.errorhandling.StepErrorStrategy;

public interface SingleStepBuilder<IN, OUT> {
  <B> SingleStepBuilder<IN, B> withMapping(MappingFunction<IN, B> f);

  SingleStepBuilder<IN, OUT> withName(String name);

  SingleStepBuilder<IN, OUT> withErrorStrategy(StepErrorStrategy errorStrategy);

  Step<IN, OUT> build();
}
