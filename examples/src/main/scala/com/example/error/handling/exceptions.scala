package com.example.error.handling

import zio.*
import zio.temporal.*
import zio.temporal.activity.*
import zio.temporal.workflow.*
import zio.temporal.failure.ActivityFailure

class ArithmeticActivityImpl()(implicit options: ZActivityOptions[Any]) extends ArithmeticActivity {
  override def divide(x: Int, y: Int): Int = {
    ZActivity.run {
      for {
        _      <- ZIO.logInfo(s"divide($x, $y)")
        result <- ZIO.attempt(x / y).refineToOrDie[ArithmeticException]
      } yield result
    }
  }
  override def multiply(x: Int, y: Int): Int = {
    ZActivity.run {
      for {
        _ <- ZIO.logInfo(s"divide($x, $y)")
      } yield x * y
    }
  }
}

class MathWorkflowImpl extends MathWorkflow {
  private lazy val logger = ZWorkflow.getLogger(getClass)

  private val activity = ZWorkflow
    .newActivityStub[ArithmeticActivity]
    .withStartToCloseTimeout(10.seconds)
    .withRetryOptions(
      ZRetryOptions.default
        .withMaximumAttempts(3)
        // Without this block, division will be retried
        .withDoNotRetry(nameOf[ArithmeticException])
    )
    .build

  override def formula(a: Int): Int = {
    val twice = activity.multiply(a, a)
    try {
      activity.divide(twice, 0)
    } catch {
      // Example of handling errors in the workflow
      case ActivityFailure.Cause(e) =>
        logger.error(s"Caught exception inside the workflow", e)
        -1
    }
  }
}