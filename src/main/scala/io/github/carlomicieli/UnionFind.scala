package io.github.carlomicieli

sealed trait UnionFind {
  def union(p: Int, q: Int): Unit

  def connected(p: Int, q: Int): Boolean

  def find(p: Int): Int

  def count: Int

  protected def validate(n: Int): Unit = {
    require((0 until count).contains(n))
  }
}

// Create: O(n)
class QuickFind(size: Int) extends UnionFind {
  private val id = (0 until size).toArray

  // O(n)
  def union(p: Int, q: Int): Unit = {
    validate(p)
    validate(q)

    val old = id(q)
    id(q) = id(p)
    replace(old, id(p))
  }

  // O(1)
  def connected(p: Int, q: Int): Boolean = {
    validate(p)
    validate(q)
    id(q) == id(p)
  }

  // O(1)
  def find(p: Int): Int = {
    validate(p)
    id(p)
  }

  val count = size

  override def toString = s"QuickFind<${id.mkString(",")}>"

  private def replace(oldVal: Int, newVal: Int): Unit = {
    val indexes = for {i <- id.indices; n = id(i); if n == oldVal} yield i
    indexes.foreach {
      i => id(i) = newVal
    }
  }
}

class QuickUnion(size: Int) extends UnionFind {
  private val id = (0 until size).toArray

  val count: Int = size

  // O(n): worst case
  def find(p: Int): Int = root(p)

  // O(n): worst case
  def connected(p: Int, q: Int): Boolean = {
    validate(p)
    validate(q)
    root(q) == root(p)
  }

  // O(1)
  def union(p: Int, q: Int): Unit = {
    validate(p)
    validate(q)
    id(p) = id(q)
  }

  private def root(p: Int): Int =
    if (p == id(p)) p
    else root(id(p))

  override def toString = s"QuickUnion<${id.mkString(",")}>"
}

object UnionFind {
  def quickFind(size: Int): UnionFind = new QuickFind(size)

  def quickUnion(size: Int): UnionFind = new QuickUnion(size)
}