package lambdaflow.impl;

import java.util.ArrayList;
import java.util.List;
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
      return this::processSynchronous;
    } else {
      return new ParallelSingleStep<>(numberThreads, mapper);
    }
  }

  private List<OUT> processSynchronous(List<IN> input) throws Exception {
    log.info("Executing step {}", name);
    var outs = new ArrayList<OUT>();
    for (IN in : input) {
      outs.add(mapper.apply(in));
    }
    return outs;
  }
}
