package io.github.carlomicieli.searching

trait SymbolTable[K, V] {
  def update(key: K, value: V): Unit
  def apply(key: K): V
  def get(key: K): Option[V]
  def delete(key: K): Unit
  def contains(key: K): Boolean
  def isEmpty: Boolean
  def size: Int

  def keys: Iterable[K]
}
