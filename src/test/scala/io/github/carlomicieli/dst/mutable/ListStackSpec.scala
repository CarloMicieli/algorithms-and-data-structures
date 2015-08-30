package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.test.AbstractTestSpec

class ListStackSpec extends AbstractTestSpec with ExampleStacks {

  "An empty stack" should "have size equals to 0" in {
    val empty = Stack.empty[Int]
    empty.size shouldBe 0
    empty.isEmpty shouldBe true
    empty.nonEmpty shouldBe false
  }

  "Push elements to a stack" should "increase its size" in {
    val stack = emptyStack
    stack.push(1)

    stack.size shouldBe 1
    stack.isEmpty shouldBe false
  }

  "Pop elements out of a stack" should "happen in LIFO fashion" in {
    val stack = emptyStack
    stack.push(1)
    stack.push(2)

    val out = stack.pop
    out shouldBe 2
  }

  "peek()" should "return the first element, without changing the stack" in {
    val first = stack.top
    first shouldBe Some(1)
  }
}

trait ExampleStacks {
  def emptyStack = Stack.empty[Int]
  def stack = {
    val st = Stack.empty[Int]
    st.push(3)
    st.push(2)
    st.push(1)
    st
  }
}
