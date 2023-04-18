package zio.temporal.fixture

import zio.*
import zio.temporal.*
import zio.temporal.activity.*
import zio.temporal.workflow.*
import zio.temporal.state.*

// Different names just to ensure uniqueness
@activityInterface
trait ZioUntypedActivity {
  @activityMethod(name = "EchoUntyped")
  def echo(what: String): String
}

class ZioUntypedActivityImpl(implicit options: ZActivityOptions[Any]) extends ZioUntypedActivity {
  override def echo(what: String): String =
    ZActivity.run {
      ZIO
        .log(s"Echo message=$what")
        .as(s"Echoed $what")
    }
}

@workflowInterface
trait ZioWorkflowUntyped {
  @workflowMethod
  def echo(what: String): String

  @signalMethod
  def complete(): Unit
}

/** Untyped version of [[ZioWorkflow]] */
class ZioWorkflowUntypedImpl extends ZioWorkflowUntyped {
  private val state  = ZWorkflowState.empty[Unit]
  private val logger = ZWorkflow.getLogger(getClass)

  private val activity = ZWorkflow.newUntypedActivityStub
    .withStartToCloseTimeout(5.seconds)
    .build

  override def echo(what: String): String = {
    val msg = activity.execute[String]("EchoUntyped", what)

    logger.info("Waiting for completion...")
    ZWorkflow.awaitWhile(state.isEmpty)
    msg
  }

  override def complete(): Unit = {
    logger.info("Completion received!")
    state := ()
  }
}