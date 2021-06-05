package lambdaflow.impl;

import java.util.ArrayList;
import java.util.List;
import lambdaflow.MappingFunction;
import lambdaflow.Step;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class SingleStep<IN, OUT> implements Step<IN, OUT> {
  private final String name;
  private final MappingFunction<IN, OUT> mapper;

  @Override
  public List<OUT> process(List<IN> input) throws Exception {
    log.info("Executing step {}", name);
    var outs = new ArrayList<OUT>();
    for (IN in : input) {
      outs.add(mapper.apply(in));
    }
    return outs;
  }
}
