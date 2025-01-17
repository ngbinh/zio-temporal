package zio.temporal.fixture

import zio._
import zio.temporal._
import zio.temporal.workflow._
import zio.temporal.activity._

@workflowInterface
trait GreetingNamedWorkflow {
  @workflowMethod(name = "GreetWf")
  def getGreeting(name: String): String
}

@workflowInterface
trait GreetingNamedChild {
  @workflowMethod(name = "ComposeGreetWf")
  def composeGreeting(greeting: String, name: String): String
}

class GreetingWorkflowNamedImpl extends GreetingNamedWorkflow {
  override def getGreeting(name: String): String = {
    val child = ZWorkflow.newChildWorkflowStub[GreetingNamedChild].build

    println("Invoking child workflow...")
    val greetingPromise = ZChildWorkflowStub.executeAsync(
      child.composeGreeting("Hello", name)
    )
    println("Child workflow started!")
    greetingPromise.run.getOrThrow
  }
}

class GreetingNamedChildImpl extends GreetingNamedChild {
  override def composeGreeting(greeting: String, name: String): String = {
    println(s"composeGreeting($greeting, $name)")
    s"$greeting $name!"
  }
}
