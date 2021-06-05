package lambdaflow;

import java.util.List;
import java.util.stream.Collectors;
import lambdaflow.impl.StepBuilderStarter;
import lambdaflow.impl.waterfall.WaterfallLambdaFlow;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenericTest {

  @Test
  void testSingle() throws Exception {
    var flow =
        singleStepFlow(
            StepBuilderStarter.<String, Integer>single().withMapping(String::length).build());

    List<String> in = List.of("first", "second");
    List<Integer> result = flow.process(in);
    Assertions.assertThat(result).containsExactly(5, 6);
  }

  @Test
  void testMulti() throws Exception {
    var flow =
        singleStepFlow(
            StepBuilderStarter.<String, Integer>multi()
                .withMapping(l -> l.stream().map(String::length).collect(Collectors.toList()))
                .build());

    List<String> in = List.of("first", "second");
    List<Integer> result = flow.process(in);
    Assertions.assertThat(result).containsExactly(5, 6);
  }

  private <A, B> LambdaFlow<A, B> singleStepFlow(Step<A, B> step) {
    return WaterfallLambdaFlow.<A>builder().addStep(step).build();
  }
}
