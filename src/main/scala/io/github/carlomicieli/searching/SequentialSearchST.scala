package io.github.carlomicieli.searching

import io.github.carlomicieli.dst.mutable.LinkedList

final class SequentialSearchST[K, V] extends SymbolTable[K, V] {
  private type KeyValPair = (K, V)
  private val storage: LinkedList[KeyValPair] = LinkedList.empty[KeyValPair]

  def update(key: K, value: V): Unit = {
    containsKey(key) foreach {
      n => storage.remove(n)
    }
    storage.addFront((key, value))
  }

  def get(key: K): Option[V] =
    containsKey(key).map(p => p._2)

  def size: Int = storage.size

  def delete(key: K): Unit = {
    containsKey(key) foreach {
      n => storage.remove(n)
    }
  }

  def keys: Iterable[K] = storage.keys.map(p => p._1)

  def apply(key: K): V = get(key).get

  def contains(key: K): Boolean =
    containsKey(key).isDefined

  def isEmpty: Boolean = storage.isEmpty

  private def containsKey(key: K): Option[KeyValPair] = {
    storage.findFirst(p => p._1 == key)
  }
}

object SequentialSearchST {
  def empty[K, V]: SymbolTable[K, V] = new SequentialSearchST[K, V]()
}