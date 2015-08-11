package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.dst.{FullStackException, EmptyStackException, Stack}
import io.github.carlomicieli.util.{Good, Bad, Or}

import scala.util.control.NoStackTrace

final class ListStack[A] private(st: LinkedList[A]) extends Stack[A] {

  private val storage: LinkedList[A] = st

  def push(item: A): Stack[A] Or FullStackException = {
    storage.addFront(item)
    Good(this)
  }

  def peek: Option[A] = st.headOption

  def size: Int = storage.size

  def isEmpty: Boolean = storage.isEmpty

  def pop(): (A, Stack[A]) Or EmptyStackException = {
    if (storage.isEmpty)
      Bad(new EmptyStackException)
    else {
      storage.removeHead.map {
        res =>
          val (k, _) = res
          (k, this)
      } orElse {
        new EmptyStackException with NoStackTrace
      }
    }
  }
}

object ListStack {
  def empty[A]: Stack[A] =
    new ListStack[A](LinkedList.empty[A])

  def apply[A](items: A*): Stack[A] = {
    val stack = ListStack.empty[A]
    if (items.isEmpty)
      stack
    else {
      for (i <- items.reverse) {
        stack.push(i)
      }
      stack
    }
  }
}
