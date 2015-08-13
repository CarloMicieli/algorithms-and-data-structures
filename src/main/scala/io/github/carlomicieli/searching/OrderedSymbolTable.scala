package io.github.carlomicieli.searching

trait OrderedSymbolTable[K, V] extends SymbolTable[K, V] {
  def min: K
  def max: K
  def floor(key: K): K
  def ceiling(key: K): K
  def rank(key: K): Int
  def select(i: Int): K
  def deleteMin(): Unit = delete(min)
  def deleteMax(): Unit = delete(max)
  def size(lo: K, hi: K): Int

  def keys(lo: K, hi: K): Iterable[K]
}
