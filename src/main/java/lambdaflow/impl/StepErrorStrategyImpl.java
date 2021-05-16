package lambdaflow.impl;

import lambdaflow.errorhandling.StepErrorStrategy;

public enum StepErrorStrategyImpl implements StepErrorStrategy {
  /** First exception aborts the step, skipping remaining items. */
  FAIL_FAST,
  /** */
  COMPLETE_AS_MANY_AS_POSSIBLE
}
