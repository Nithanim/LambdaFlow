package lambdaflow;

import java.util.List;

public interface Step<IN, OUT> {
  List<OUT> process(List<IN> input) throws Exception;
}
