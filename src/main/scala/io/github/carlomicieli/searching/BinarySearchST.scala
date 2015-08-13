package io.github.carlomicieli.searching

import io.github.carlomicieli.dst.mutable.DynamicArray

import scala.reflect.ClassTag

final class BinarySearchST[K: Ordering, V] private (st: DynamicArray[KeyValuePair[K, V]]) extends SymbolTable[K, V] {

  private var s = 0

  def update(key: K, value: V): Unit = ???

  def get(key: K): Option[V] = ???

  def size: Int = s

  def delete(key: K): Unit = ???

  def keys: Iterable[K] = ???

  def apply(key: K): V = ???

  def contains(key: K): Boolean = ???

  def isEmpty: Boolean = s == 0

  override def toString = st.toString

  private def isFull = false

  private def binarySearch(k: K): Option[Int] = ???
}

object BinarySearchST {
  def apply[K: ClassTag, V: ClassTag](size: Int)(implicit ord: Ordering[K]): SymbolTable[K, V] = {
    val st = DynamicArray.empty[KeyValuePair[K, V]](size)
    new BinarySearchST[K, V](st)
  }
}

case class KeyValuePair[K: Ordering, V](key: K, value: V)

object KeyValuePair {

  implicit def pairOrdering[K: Ordering, V] = new Ordering[KeyValuePair[K, V]] {
    def compare(x: KeyValuePair[K, V], y: KeyValuePair[K, V]): Int = {
      import Ordered._
      x.key.compare(y.key)
    }
  }
}