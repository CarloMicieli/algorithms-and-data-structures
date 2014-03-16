package io.github.carlomicieli.algo

trait Sort {
  def sort[A](arr: Array[A])(implicit ord: math.Ordering[A]): Array[A]

  def swap[A](arr: Array[A], i: Int, j: Int): Unit = {
    val tmp = arr(i);
    arr(i) = arr(j)
    arr(j) = tmp
  }

  def less[A](arr: Array[A], i: Int, j: Int)(implicit ord: math.Ordering[A]): Boolean = {
    ord.compare(arr(i), arr(j)) <= 0
  }

  def isSorted[A](arr: Array[A])(implicit ord: math.Ordering[A]): Boolean = {
    type Pair = Tuple2[A, Boolean]
    val initAcc: Pair = (arr.head, true)
    val op = (acc: Pair, n: A) => (n, acc._2 && ord.compare(acc._1, n) <= 0)

    val (max, sorted) = arr.foldLeft(initAcc)(op)
    sorted
  }
}

object Insertion extends Sort {
  def sort[A](arr: Array[A])(implicit ord: math.Ordering[A]): Array[A] = {

    var i, j = 0
    var end = arr.size

    while (i < end) {
      j = i
      while (j > 0 && less(arr, j, j - 1)) {
        swap(arr, j, j - 1)
        j = j - 1
      }
      i = i + 1
    }

    arr
  }
}

object Bubble extends Sort {
  def sort[A](arr: Array[A])(implicit ord: math.Ordering[A]): Array[A] = {
    var sorted = false
    var i, j = 0
    var end = arr.size - 1

    while (!sorted) {
      sorted = true
      i = 0
      while (i < end) {
        j = i + 1
        if (less(arr, j, i)) {
          swap(arr, i, j)
          sorted = false
        }

        i = i + 1
      }
      end = end - 1
    }

    arr
  }
}
