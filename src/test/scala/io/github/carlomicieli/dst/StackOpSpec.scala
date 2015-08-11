package io.github.carlomicieli.dst

import io.github.carlomicieli.util.{Good, Bad}
import io.github.carlomicieli.dst.mutable.FixedCapacityStack
import org.scalatest.{Matchers, FlatSpec}

class StackOpSpec extends FlatSpec with Matchers with SampleStacks {
  "A sequence of valid ops" should "produce valid stacks" in {
    import StackOp._

    val res = sequence(emptyStack,
      List(PushOp(1), PushOp(2), PushOp(3), PopOp, PopOp, PushOp(5)))

    val Good(finalStack) = res
    finalStack.isEmpty should be(false)
    finalStack.size should be(2)
    finalStack.peek.get should be(5)
  }

  "A sequence of invalid operation" should "produce an Bad result" in {
    import StackOp._

    val res = sequence(emptyStack,
      List(PushOp(1), PushOp(2), PushOp(3), PopOp, PopOp, PopOp, PopOp, PushOp(5)))

    val Bad(invalidOp) = res
    invalidOp.op should be(PopOp)
    invalidOp.ex.getMessage should be("Stack is empty")
    invalidOp.stack.isEmpty should be(true)
  }
}

trait SampleStacks {
  def emptyStack: Stack[Int] = FixedCapacityStack(10)
}