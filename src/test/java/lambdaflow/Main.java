package lambdaflow;

import java.util.EventObject;
import java.util.List;
import lambdaflow.impl.FlowErrorStrategyImpl;
import lambdaflow.impl.LambdaFlowImpl;
import lambdaflow.impl.StepBuilderStarter;
import lambdaflow.impl.StepErrorStrategyImpl;

public class Main {
  public static void main(String[] args) {
    var flow =
        LambdaFlowImpl.builder(EventObject.class)
            .addStep(
                StepBuilderStarter.<EventObject, String>single()
                    .withName("first")
                    .withMapping(EventObject::toString)
                    .withErrorStrategy(StepErrorStrategyImpl.FAIL_FAST)
                    .build())
            .withErrorStrategy(FlowErrorStrategyImpl.FAIL_FAST)
            .build();

    List<EventObject> data = List.of(new EventObject(""));
    List<String> r = flow.process(data);
  }
}
