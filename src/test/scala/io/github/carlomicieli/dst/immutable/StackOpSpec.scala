package io.github.carlomicieli.dst.immutable

import io.github.carlomicieli.test.AbstractTestSpec
import io.github.carlomicieli.util.{Just, Good, Bad}

class StackOpSpec extends AbstractTestSpec with SampleStacks {
  "A sequence of valid ops" should "produce valid stacks" in {
    import StackOp._
    val res = sequence(emptyStack,
      List(PushOp(1), PushOp(2), PushOp(3), PopOp, PopOp, PushOp(5)))

    val Good(finalStack) = res
    finalStack.isEmpty shouldBe false
    finalStack.size shouldBe 2
    finalStack.top shouldBe Just(5)
  }

  "A sequence of invalid operation" should "produce an Bad result" in {
    import StackOp._

    val res = sequence(emptyStack,
      List(PushOp(1), PushOp(2), PushOp(3), PopOp, PopOp, PopOp, PopOp, PushOp(5)))

    val Bad(invalidOp) = res
    invalidOp.op shouldBe PopOp
    invalidOp.ex.getMessage shouldBe "Stack is empty"
    invalidOp.stack.isEmpty shouldBe true
  }
}

trait SampleStacks {
  def emptyStack: Stack[Int] = Stack.empty[Int]
}
