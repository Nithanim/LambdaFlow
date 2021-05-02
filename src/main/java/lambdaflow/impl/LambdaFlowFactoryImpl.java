package lambdaflow.impl;

import lambdaflow.LambdaFlowBuilder;
import lambdaflow.LambdaFlowFactory;
import lambdaflow.StepBuilder;

public class LambdaFlowFactoryImpl implements LambdaFlowFactory {
  @Override
  public <T> LambdaFlowBuilder<T, T> flow(Class<T> dataClass) {
    return new LambdaFlowBuilderImpl<>();
  }

  @Override
  public <A, B> StepBuilder<A, B> step() {
    throw new UnsupportedOperationException();
  }
}
