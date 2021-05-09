package lambdaflow.impl;

import java.util.List;
import lambdaflow.MappingFunction;
import lambdaflow.MultiStepBuilder;
import lambdaflow.Step;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultiStepBuilderImpl<IN, OUT> implements MultiStepBuilder<IN, OUT> {
  private String name;
  private MappingFunction<List<IN>, List<OUT>> mapper;

  @SuppressWarnings("unchecked")
  @Override
  public <B> MultiStepBuilderImpl<IN, B> withMapping(MappingFunction<List<IN>, List<B>> f) {
    this.mapper = (MappingFunction<List<IN>, List<OUT>>) (MappingFunction<?, ?>) f;
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
      public List<OUT> process(List<IN> input) throws Exception {
        log.info("Executing step {}", name);
        return mapper.apply(input);
      }
    };
  }
}
