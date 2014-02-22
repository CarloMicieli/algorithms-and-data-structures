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
  private val root = (0 until size).toArray

  val count: Int = size

  def find(p: Int): Int = findRoot(p)

  def connected(p: Int, q: Int): Boolean = {
    validate(p)
    validate(q)
    findRoot(q) == findRoot(p)
  }

  def union(p: Int, q: Int): Unit = {
    validate(p)
    validate(q)
    root(p) = root(q)
  }

  private def findRoot(id: Int): Int =
    if (id == root(id)) id
    else findRoot(root(id))

  override def toString = s"QuickUnion<${root.mkString(",")}>"
}

object UnionFind {
  def quickFind(size: Int): UnionFind = new QuickFind(size)

  def quickUnion(size: Int): UnionFind = new QuickUnion(size)
}