package lambdaflow;

import java.util.List;

public interface LambdaFlow<IN, OUT> {
  List<OUT> process(List<IN> input);
}
