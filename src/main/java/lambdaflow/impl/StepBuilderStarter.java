package lambdaflow.impl;

import java.util.function.Function;

public class StepBuilderStarter {
  public static <A, B> SingleStepBuilderImpl<A, B> single(Function<A, B> f) {
    return StepBuilderStarter.<A, B>single().withMapping(f);
  }

  public static <A, B> SingleStepBuilderImpl<A, B> single() {
    return new SingleStepBuilderImpl<>();
  }
}
