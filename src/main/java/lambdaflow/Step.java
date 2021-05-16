package lambdaflow;

import java.util.List;

public interface Step<IN, OUT> extends AutoCloseable {
  List<OUT> process(List<IN> input) throws Exception;

  @Override
  default void close() {}
}
