package io.github.carlomicieli.dst

trait Container[A] {
  def forEach[U](f: A => U): Unit
  def size: Int
  def isEmpty: Boolean
  def nonEmpty = !isEmpty
  def contains(x: A): Boolean
  def mkString(sep: String): String
}