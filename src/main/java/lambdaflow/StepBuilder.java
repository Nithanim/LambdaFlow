package lambdaflow;

public interface StepBuilder<IN, OUT> {

  StepBuilder<IN, OUT> withName(String name);

  StepBuilder<IN, OUT> withErrorHandling(Object e);
}
