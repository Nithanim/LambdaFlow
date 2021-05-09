package lambdaflow.impl;

import java.util.List;
import lambdaflow.MappingFunction;

public class StepBuilderStarter {
  public static <A, B> SingleStepBuilderImpl<A, B> single(MappingFunction<A, B> f) {
    return StepBuilderStarter.<A, B>single().withMapping(f);
  }

  public static <A, B> SingleStepBuilderImpl<A, B> single() {
    return new SingleStepBuilderImpl<>();
  }

  public static <A, B> MultiStepBuilderImpl<A, B> multi(MappingFunction<List<A>, List<B>> f) {
    return StepBuilderStarter.<A, B>multi().withMapping(f);
  }

  public static <A, B> MultiStepBuilderImpl<A, B> multi() {
    return new MultiStepBuilderImpl<>();
  }
}
