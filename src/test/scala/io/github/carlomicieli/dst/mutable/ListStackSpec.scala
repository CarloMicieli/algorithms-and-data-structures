package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.util.Good
import org.scalatest.{Matchers, FlatSpec}

class ListStackSpec extends FlatSpec with Matchers with ExampleStacks {

  "An empty stack" should "have size equals to 0" in {
    val empty = ListStack.empty[Int]
    empty.size should be(0)
    empty.isEmpty should be(true)
    empty.nonEmpty should be(false)
  }

  "Push elements to a stack" should "increase its size" in {
    val stack = emptyStack
    stack.push(1)

    stack.size should be(1)
    stack.isEmpty should be(false)
  }

  "Pop elements out of a stack" should "happen in LIFO fashion" in {
    val stack = emptyStack
    stack.push(1)
    stack.push(2)

    val Good((out, _)) = stack.pop
    out should be(2)
  }

  "Pop elements out of empty stacks" should "return a Bad value" in {
    val res = emptyStack.pop
    res.isBad should be(true)
  }

  "A Stack" should "be filled with values" in {
    val s = ListStack(1, 2, 3)

    val Good((k, _)) = s.pop
    k should be(1)
  }

  "peek()" should "return the first element, without changing the stack" in {
    val first = stack.peek
    first should be(Some(1))
  }
}


trait ExampleStacks {
  def emptyStack = ListStack.empty[Int]
  def stack = ListStack(1, 2, 3)
}
