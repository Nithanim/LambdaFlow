package lambdaflow.impl;

import lambdaflow.MappingFunction;
import lambdaflow.SingleStepBuilder;
import lambdaflow.Step;
import lambdaflow.errorhandling.StepErrorStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SingleStepBuilderImpl<IN, OUT> implements SingleStepBuilder<IN, OUT> {
  private String name;
  private MappingFunction<IN, OUT> mapper;
  private StepErrorStrategy errorStrategy;
  private int numberThreads = 0;

  @Override
  @SuppressWarnings("unchecked")
  public <B> SingleStepBuilderImpl<IN, B> withMapping(MappingFunction<IN, B> f) {
    this.mapper = (MappingFunction<IN, OUT>) f;
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
    if (numberThreads == 0) {
      return new SingleStep<>(name, mapper);
    } else {
      return new ParallelSingleStep<>(name, mapper, numberThreads);
    }
  }
}
