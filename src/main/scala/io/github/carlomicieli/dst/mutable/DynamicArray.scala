package io.github.carlomicieli.dst.mutable

import scala.reflect.ClassTag

final class DynamicArray[A: ClassTag] private(st: Array[A]) {

  def update(i: Int, v: A): Unit = st(i) = v

  def apply(i: Int): A = st(i)

  def length: Int = st.length

  def expand: DynamicArray[A] = resize(3.0 / 2)
  def shrink: DynamicArray[A] = resize(2.0 / 3)

  def resize(ratio: Double): DynamicArray[A] = {
    val newCapacity = (st.length * ratio).toInt
    val newArr = DynamicArray.empty[A](newCapacity)
    for (i <- 0 until math.min(newCapacity, st.length)) {
      newArr(i) = st(i)
    }
    newArr
  }

  def shift(n: Int, s: Int): Unit = {
    import IntImplicits._
    for (i <- last downTo (s + n)) {
      st(i) = st(i - n)
    }
  }

  def insert(el: A)(p: (A, A) => Boolean): Boolean = {
    @annotation.tailrec
    def loop(i: Int): Option[Int] = {
      if (i >= length) None
      else {
        if (p(el, st(i))) Some(i) else loop(i + 1)
      }
    }

    loop(0).map { i =>
        shift(1, i)
        update(i, el)
        i
    }.isDefined
  }

  def swap(i: Int, j: Int): Unit = {
    if (i != j) {
      val tmp = st(i)
      st(i) = st(j)
      st(j) = tmp
    }
  }

  def elements: Iterable[A] = new Iterable[A] {
    def iterator: Iterator[A] = new Iterator[A] {
      private var curr = 0
      def hasNext: Boolean = curr < st.length
      def next(): A = {
        val el = st(curr)
        curr = curr + 1
        el
      }
    }
  }

  override def toString = {
    val items = st.mkString(", ")
    s"[$items]"
  }

  private def last: Int = st.length - 1
}

object DynamicArray {
  def apply[A: ClassTag](el: A, items: A*): DynamicArray[A] = {
    val arr = DynamicArray.empty[A](items.length + 1)
    arr(0) = el
    for (i <- 0 until items.length) {
      arr(i + 1) = items(i)
    }
    arr
  }

  def empty[A: ClassTag](size: Int): DynamicArray[A] = {
    val st = new Array[A](size)
    new DynamicArray[A](st)
  }
}

object IntImplicits {
  implicit class IntOps(val n: Int) extends AnyVal {
    def downTo(start: Int): Range = (start to n).reverse
  }
}