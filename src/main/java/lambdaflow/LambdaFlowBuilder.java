package lambdaflow;

import lambdaflow.errorhandling.FlowErrorStrategy;

public interface LambdaFlowBuilder<IN, OUT> {
  <T> LambdaFlowBuilder<IN, T> addStep(Step<OUT, T> step);

  LambdaFlowBuilder<IN, OUT> withErrorStrategy(FlowErrorStrategy errorStrategy);

  LambdaFlow<IN, OUT> build();
}
