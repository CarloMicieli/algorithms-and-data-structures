package io.github.carlomicieli.dst

trait Bag[A] {
  def add(a: A): Unit
  def isEmpty: Boolean
  def size: Int
  def contains(el: A): Boolean
}