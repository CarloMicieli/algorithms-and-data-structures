package io.github.carlomicieli

/**
 * Union/find implementation
 *
 * Robert Sedgewick, Kevin Wayne
 * Algorithms, 4th Edition
 * 2011, Addison-Wesley Professional
 */
sealed trait UnionFind {
  def union(p: Int, q: Int): Unit

  def connected(p: Int, q: Int): Boolean

  def find(p: Int): Int

  def count: Int

  protected def validate(n: Int): Unit = {
    require((0 until count).contains(n))
  }
  
  protected def root(id: Array[Int], el: Int): Int =
    if (el == id(el)) el
    else root(id, id(el))

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

  private def root(p: Int): Int = root(id, p)

  override def toString = s"QuickUnion<${id.mkString(",")}>"
}

class WeightedUnion(size: Int) extends UnionFind {
  private val id = (0 until size).toArray
  private val sz = Array.fill(size)(1)

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

    val i = root(p)
    val j = root(q)
    if (i != j) {
      if (weight(i) < weight(j)) linkTrees(i, j)
      else linkTrees(j, i)
    }
  }

  private def linkTrees(smaller: Int, bigger: Int): Unit = {
    id(smaller) = bigger
    sz(bigger) += sz(smaller)
  }

  private def weight(el: Int) = sz(el)
  private def root(p: Int): Int = root(id, p)

  override def toString = s"WeightedUnion<${id.mkString(",")}>"
}

object UnionFind {
  def quickFind(size: Int): UnionFind = new QuickFind(size)

  def quickUnion(size: Int): UnionFind = new QuickUnion(size)

  def weightedUnion(size: Int): UnionFind = new WeightedUnion(size)
}