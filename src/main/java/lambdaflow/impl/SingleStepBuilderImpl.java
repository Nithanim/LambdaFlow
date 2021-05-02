package lambdaflow.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lambdaflow.SingleStepBuilder;
import lambdaflow.Step;
import lambdaflow.errorhandling.StepErrorStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SingleStepBuilderImpl<IN, OUT> implements SingleStepBuilder<IN, OUT> {
  private String name;
  private Function<IN, OUT> mapper;
  private StepErrorStrategy errorStrategy;

  @Override
  @SuppressWarnings("unchecked")
  public <B> SingleStepBuilderImpl<IN, B> withMapping(Function<IN, B> f) {
    this.mapper = (Function<IN, OUT>) f;
    return (SingleStepBuilderImpl<IN, B>) this;
  }

  @Override
  public SingleStepBuilderImpl<IN, OUT> withName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public SingleStepBuilderImpl<IN, OUT> withErrorStrategy(StepErrorStrategy errorStrategy) {
    this.errorStrategy = errorStrategy;
    return this;
  }

  @Override
  public Step<IN, OUT> build() {
    return new Step<>() {
      @Override
      public List<OUT> process(List<IN> input) {
        log.info("Executing step {}", name);
        return input.stream().map(mapper).collect(Collectors.toList());
      }
    };
  }
}
