We are using java lambdas at work all the time, but we keep reinventing the wheel when it comes to logic flow.

The processing steps are easily defined for a single item. However, the batch feature is often desired, where multiple
items are passed to the lambda to reduce the overhead of the call.

This could bring some advantages to the table, like batch requests to an external resource. Though, there are also
disadvantages, like collecting and splitting the items again for such a batch request or more complex error handling.

This library aims to provide a framework that allows defining a flow with simple steps that make up a complete
processing pipeline.
