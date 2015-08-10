package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.dst.{EmptyStackException, Stack}
import io.github.carlomicieli.util.{Good, Bad, Or}

final class LinkedListStack[A] private(st: LinkedList[A]) extends Stack[A] {

  private val storage: LinkedList[A] = st

  def push(item: A): Stack[A] = {
    storage.addFront(item)
    this
  }

  def peek: Option[A] = ???

  def size: Int = storage.size

  def isEmpty: Boolean = storage.isEmpty

  def pop: Or[(A, Stack[A]), EmptyStackException] = {
    if (storage.isEmpty)
      Bad(new EmptyStackException)
    else {
      val (k, _) = storage.removeHead
      Good((k, this))
    }
  }
}

object LinkedListStack {
  def empty[A]: Stack[A] =
    new LinkedListStack[A](LinkedList.empty[A])

  def apply[A](items: A*): Stack[A] = ???
}
