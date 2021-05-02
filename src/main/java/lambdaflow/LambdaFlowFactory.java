package lambdaflow;

public interface LambdaFlowFactory {
  <T> LambdaFlowBuilder<T, T> flow(Class<T> dataClass);

  <A, B> StepBuilder<A, B> step();
}
