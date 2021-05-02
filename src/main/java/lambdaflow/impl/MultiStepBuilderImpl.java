package lambdaflow.impl;

import java.util.List;
import java.util.function.Function;
import lambdaflow.MultiStepBuilder;
import lambdaflow.Step;

public class MultiStepBuilderImpl<IN, OUT> implements MultiStepBuilder<IN, OUT> {
  private String name;
  private Function<List<IN>, List<OUT>> mapper;

  @SuppressWarnings("unchecked")
  @Override
  public <B> MultiStepBuilderImpl<IN, B> withMapping(Function<List<IN>, List<B>> f) {
    this.mapper = (Function<List<IN>, List<OUT>>) (Function<?, ?>) f;
    return (MultiStepBuilderImpl<IN, B>) this;
  }

  @Override
  public MultiStepBuilderImpl<IN, OUT> withName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public Step<IN, OUT> build() {
    return new Step<>() {
      @Override
      public List<OUT> process(List<IN> input) {
        return mapper.apply(input);
      }
    };
  }
}
