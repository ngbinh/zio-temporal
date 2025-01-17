# Overview

<head>
  <meta charset="UTF-8" />
  <meta name="description" content="ZIO Temporal overview" />
  <meta name="keywords" content="ZIO Temporal overview" />
</head>

`zio-temporal` is an integration with [Temporal workflow](https://temporal.io) based on Java SDK and ZIO.  
It allows you to run workflows in the Scala way!

Video materials:
- [TL;DR intro about Temporal workflow](https://youtu.be/2HjnQlnA5eY)
- [Functional Scala 2022: Build Invincible Apps With ZIO And Temporal](https://youtu.be/8MUnEahr5tk)

Example projects using `zio-temporal`:
- [Basic error handling](https://github.com/vitaliihonta/zio-temporal/tree/main/examples/src/main/scala/com/example/error/handling)
- [Child workflows](https://github.com/vitaliihonta/zio-temporal/tree/main/examples/src/main/scala/com/example/child)
- [External workflows](https://github.com/vitaliihonta/zio-temporal/tree/main/examples/src/main/scala/com/example/externalwf)
- [Booking saga](https://github.com/vitaliihonta/zio-temporal/tree/main/examples/src/main/scala/com/example/bookingsaga)
- [Parameterization & Generics](https://github.com/vitaliihonta/zio-temporal/tree/main/examples/src/main/scala/com/example/generics)
- [Payments](https://github.com/vitaliihonta/zio-temporal/tree/main/examples/src/main/scala/com/example/payments)
- [Long-running workflows](https://github.com/vitaliihonta/zio-temporal/tree/main/examples/src/main/scala/com/example/heartbeatingactivity)
- [Functional Scala 2022: Cryptocurrency exchange](https://github.com/vitaliihonta/zio-temporal-samples/tree/main/cryptostock)
- [Content Sync](https://github.com/vitaliihonta/zio-temporal-samples/tree/main/content-sync)

## Installation

```scala
// Core
libraryDependencies += "@ORGANIZATION@" %% "zio-temporal-core" % "@VERSION@"

// Protobuf transport
libraryDependencies += "@ORGANIZATION@" %% "zio-temporal-protobuf" % "@VERSION@"

// Testkit
libraryDependencies += "@ORGANIZATION@" %% "zio-temporal-testkit" % "@VERSION@"
```

## Modules

1. **zio-temporal-core** - ZIO integration and basic wrappers that bring type safety to your workflows.  
   Allows you to use arbitrary ZIO code in your activities
2. **zio-temporal-protobuf** - integration with [ScalaPB](https://scalapb.github.io/) which allows you to
   use [Protobuf](https://developers.google.com/protocol-buffers)  
   as a transport layer protocol for communication with Temporal cluster
3. **zio-temporal-testkit** - wrappers for `temporal-testkit` module which allows to test your workflows locally in unit tests. 
