package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.util.Good
import org.scalatest.{Matchers, FlatSpec}

class LinkedListStackSpec extends FlatSpec with Matchers with ExampleStacks {

  "An empty stack" should "have size equals to 0" in {
    val empty = LinkedListStack.empty[Int]
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
}


trait ExampleStacks {
  def emptyStack = LinkedListStack.empty[Int]
}
