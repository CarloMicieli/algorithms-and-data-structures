package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.dst.Bag

import scala.reflect.ClassTag

final class FixedCapacityBag[A] private(st: Array[A]) extends Bag[A] {

  private val storage = st
  private var ind = 0

  def add(el: A): Unit = {
    storage(ind) = el
    ind = ind + 1
  }

  def size: Int = ind
  def contains(el: A): Boolean = {
    @annotation.tailrec
    def loop(i: Int): Boolean =
      if (i == ind) false
      else {
        if (storage(i) == el) true
        else loop(i + 1)
      }

    loop(0)
  }

  def isEmpty: Boolean = ind == 0
}

object FixedCapacityBag {
  def apply[A: ClassTag](size: Int): Bag[A] = {
    val storage = new Array[A](size)
    new FixedCapacityBag[A](storage)
  }
}