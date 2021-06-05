package lambdaflow.impl.waterfall;

import java.util.ArrayList;
import java.util.List;
import lambdaflow.LambdaFlowBuilder;
import lambdaflow.Step;
import lambdaflow.errorhandling.FlowErrorStrategy;

public class WaterfallLambdaFlowBuilder<IN, OUT> implements LambdaFlowBuilder<IN, OUT> {
  private final List<Step<?, ?>> steps = new ArrayList<>();
  private FlowErrorStrategy errorStrategy;

  @SuppressWarnings("unchecked")
  @Override
  public <T> LambdaFlowBuilder<IN, T> addStep(Step<OUT, T> step) {
    steps.add(step);
    return (WaterfallLambdaFlowBuilder<IN, T>) this;
  }

  @Override
  public WaterfallLambdaFlowBuilder<IN, OUT> withErrorStrategy(FlowErrorStrategy errorStrategy) {
    this.errorStrategy = errorStrategy;
    return this;
  }

  @Override
  public WaterfallLambdaFlow<IN, OUT> build() {
    return new WaterfallLambdaFlow<>(List.copyOf(this.steps), errorStrategy);
  }
}
