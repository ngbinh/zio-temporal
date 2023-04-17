package zio.temporal.internal

import zio.temporal.workflow.ZExternalWorkflowStub
import scala.reflect.macros.blackbox

class ZExternalSignalMacro(override val c: blackbox.Context) extends InvocationMacroUtils(c) {
  import c.universe._

  private val zexternalWorkflowStub = typeOf[ZExternalWorkflowStub.type].dealias

  def signalImpl(f: Expr[Unit]): Tree = {
    // Assert called on ZExternalWorkflowStub
    val _ = getPrefixOf(zexternalWorkflowStub)

    val tree       = f.tree
    val invocation = getMethodInvocation(tree)
    assertWorkflow(invocation.instance.tpe)

    val method = invocation.getMethod(SharedCompileTimeMessages.sgnlMethodShouldntBeExtMethod)
    method.assertSignalMethod()
    val signalName = getSignalName(method.symbol)

    q"""
       _root_.zio.temporal.internal.TemporalWorkflowFacade.signal(
         ${invocation.instance}.toJava,
         $signalName,
         ${invocation.args}
       )
    """.debugged(SharedCompileTimeMessages.generatedSignal)
  }
}