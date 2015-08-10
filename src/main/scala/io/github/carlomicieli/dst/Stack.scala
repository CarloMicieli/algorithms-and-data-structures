package io.github.carlomicieli.dst

import io.github.carlomicieli.util.Or

trait Stack[A] {
  def push(item: A): Stack[A]
  def peek: Option[A]
  def pop: (A, Stack[A]) Or EmptyStackException
  def isEmpty: Boolean
  def nonEmpty: Boolean = !isEmpty

  def size: Int
}

final class InvalidStackOperationException extends Exception("Stack: invalid operation")
final class EmptyStackException extends Exception("Stack is empty")