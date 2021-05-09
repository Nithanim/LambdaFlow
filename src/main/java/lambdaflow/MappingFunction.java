package lambdaflow;

@FunctionalInterface
public interface MappingFunction<IN, OUT> {
  OUT apply(IN in) throws Exception;
}
