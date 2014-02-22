package io.github.carlomicieli

sealed trait UnionFind {
  def union(p: Int, q: Int): Unit

  def connected(p: Int, q: Int): Boolean

  def find(p: Int): Int

  def count: Int
}

// Create: O(n)
class QuickFind(size: Int) extends UnionFind {
  private val id = (0 to size - 1).toArray

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

  def count = id.length

  override def toString = s"UF<${id.mkString(",")}>"

  private def validate(n: Int): Unit = {
    require((0 to size - 1).contains(n))
  }

  private def replace(oldVal: Int, newVal: Int): Unit = {
    val indexes = for {i <- id.indices; n = id(i); if n == oldVal} yield i
    indexes.foreach {
      i => id(i) = newVal
    }
  }
}

object UnionFind {
  def apply(size: Int): UnionFind = new QuickFind(size)
}