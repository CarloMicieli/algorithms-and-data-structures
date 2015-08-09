package io.github.carlomicieli.dst.mutable

final class MaxPQ[A] private (storage: Array[A], size: Int) {
  private var hSize = size

  def heapSize = hSize
  def length = storage.length


  private def heapify(array: Array[A], i: Int)(implicit ord: Ordering[A]): Unit = {
    import Ordered._
    val maxPrio: (Int, Int) => Boolean = (i, j) => {
      val x = a(array, i)
      val y = a(array, j)
      x > y
    }
    val maxHeapify = checkProp(maxPrio)(array, _: Int)

    maxHeapify(i) match {
      case None => ()
      case Some(largest) =>
        swap(array, i, largest)
        heapify(array, largest)
    }
  }

  private def swap(array: Array[A], i: Int, j: Int): Unit = {
    val ii = i - 1
    val ij = j - 1
    val tmp: A = array(ii)
    array(ii) = array(ij)
    array(ij) = tmp
  }

  private def checkProp(prop: (Int, Int) => Boolean)(array: Array[A], i: Int): Option[Int] = {
    val lt = left(i)
    val rt = right(i)

    val leftOp = if (lt <= heapSize && !prop(i, lt))
      Some(lt) else None

    val rightOp = if (rt <= heapSize && !prop(i, rt))
      Some(rt) else None

    (leftOp, rightOp) match {
      case (None, None)       => None
      case (None, r)          => r
      case (l, None)          => l
      case (Some(l), Some(r)) => if (prop(l, r)) Some(l) else Some(r)
    }
  }

  private def parent(i: Int): Int = i >> 1
  private def left(i: Int): Int = i << 1
  private def right(i: Int): Int = 1 + (i << 1)

  private def a(array: Array[A], i: Int): A = array(i - 1)
  private def a(i: Int): A = storage(i - 1)

  override def toString = {
    val s = storage.toList.take(heapSize).mkString(", ")
    s"MaxPQ($s)"
  }
}


object MaxPQ {
  implicit class IntOps(val i: Int) extends AnyVal {
    def downTo(n: Int) = n.to(i).reverse
  }

  def apply[A: scala.reflect.ClassTag](capacity: Int): MaxPQ[A] = {
    val st = new Array[A](capacity)
    new MaxPQ[A](st, 0)
  }

  def apply[A](array: Array[A])(implicit ord: Ordering[A]): MaxPQ[A] = {
    val N = array.length
    val heap = new MaxPQ[A](array, N)
    for (i <- (N / 2) downTo 1) {
      heap.heapify(array, i)
    }
    heap
  }
}

