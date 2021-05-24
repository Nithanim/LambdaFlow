package lambdaflow;

import java.util.List;
import java.util.stream.Collectors;
import lambdaflow.impl.FlowErrorStrategyImpl;
import lambdaflow.impl.LambdaFlowImpl;
import lambdaflow.impl.StepBuilderStarter;
import lambdaflow.impl.StepErrorStrategyImpl;

public class Main {
  public static void main(String[] args) throws Exception {

    var flow =
        LambdaFlowImpl.<String>builder()
            .addStep(
                StepBuilderStarter.<String, A>single()
                    .withName("first")
                    .withMapping(A::new)
                    .withErrorStrategy(StepErrorStrategyImpl.FAIL_FAST)
                    .build())
            .addStep(
                StepBuilderStarter.<A, B>multi()
                    .withName("second")
                    .withMapping(Main::secondStepFunction)
                    .build())
            .addStep(
                StepBuilderStarter.<B, Integer>single()
                    .withName("third")
                    .withMapping(B::getI)
                    .build())
            .withErrorStrategy(FlowErrorStrategyImpl.FAIL_FAST)
            .build();

    List<String> in = List.of("ABCD", "1234567");
    System.out.println(in);
    List<Integer> out = flow.process(in);
    System.out.println(out);
  }

  private static List<B> secondStepFunction(List<A> l) {
    return l.stream().map(e -> new B(e.getS().length())).collect(Collectors.toList());
  }
}
