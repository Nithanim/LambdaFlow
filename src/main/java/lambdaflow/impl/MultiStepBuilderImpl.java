package lambdaflow.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lambdaflow.MappingFunction;
import lambdaflow.MultiStepBuilder;
import lambdaflow.Step;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultiStepBuilderImpl<IN, OUT> implements MultiStepBuilder<IN, OUT> {
  private String name;
  private MappingFunction<List<IN>, List<OUT>> mapper;
  private Function<IN, String> groupingFunction;

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
  public MultiStepBuilder<IN, OUT> withGrouping(Function<IN, String> groupingFunction) {
    this.groupingFunction = groupingFunction;
    return this;
  }

  @Override
  public Step<IN, OUT> build() {
    if (groupingFunction != null) {
      return createNoGroupingStep();
    } else {
      return createGroupingStep();
    }
  }

  private Step<IN, OUT> createGroupingStep() {
    return input -> {
      log.info("Executing step {}", name);
      Map<String, List<IN>> groups =
          input.stream().collect(Collectors.groupingBy(groupingFunction));
      var results = new ArrayList<OUT>();
      for (List<IN> group : groups.values()) {
        results.addAll(mapper.apply(group));
      }
      return results;
    };
  }

  private Step<IN, OUT> createNoGroupingStep() {
    return input -> {
      log.info("Executing step {}", name);
      return mapper.apply(input);
    };
  }
}
