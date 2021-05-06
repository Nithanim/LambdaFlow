package lambdaflow.impl;

import java.util.List;
import java.util.function.Function;

public class StepBuilderStarter {
  public static <A, B> SingleStepBuilderImpl<A, B> single(Function<A, B> f) {
    return StepBuilderStarter.<A, B>single().withMapping(f);
  }

  public static <A, B> SingleStepBuilderImpl<A, B> single() {
    return new SingleStepBuilderImpl<>();
  }

  public static <A, B> MultiStepBuilderImpl<A, B> multi(Function<List<A>, List<B>> f) {
    return StepBuilderStarter.<A, B>multi().withMapping(f);
  }

  public static <A, B> MultiStepBuilderImpl<A, B> multi() {
    return new MultiStepBuilderImpl<>();
  }
}
