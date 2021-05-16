package lambdaflow;

import java.util.List;

public interface LambdaFlow<IN, OUT> extends AutoCloseable {
  List<OUT> process(List<IN> input) throws Exception;
}
