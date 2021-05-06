package lambdaflow.impl;

import java.util.ArrayList;
import java.util.List;
import lambdaflow.LambdaFlowBuilder;
import lambdaflow.Step;
import lambdaflow.errorhandling.FlowErrorStrategy;

public class LambdaFlowBuilderImpl<IN, OUT> implements LambdaFlowBuilder<IN, OUT> {
  private final List<Step<?, ?>> steps = new ArrayList<>();
  private FlowErrorStrategy errorStrategy;

  @SuppressWarnings("unchecked")
  @Override
  public <T> LambdaFlowBuilder<IN, T> addStep(Step<OUT, T> step) {
    steps.add(step);
    return (LambdaFlowBuilderImpl<IN, T>) this;
  }

  @Override
  public LambdaFlowBuilderImpl<IN, OUT> withErrorStrategy(FlowErrorStrategy errorStrategy) {
    this.errorStrategy = errorStrategy;
    return this;
  }

  @Override
  public LambdaFlowImpl<IN, OUT> build() {
    return new LambdaFlowImpl<>(List.copyOf(this.steps), errorStrategy);
  }
}
