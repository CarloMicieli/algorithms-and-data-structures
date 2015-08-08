package io.github.carlomicieli.sorting

import com.typesafe.scalalogging.LazyLogging

import scala.reflect.ClassTag

object Shell extends Sorting with LazyLogging {
  def name: String = "Shell sort"

  def sort[A: ClassTag](array: Array[A], start: Int, end: Int)
                       (implicit ord: Ordering[A]): Unit = {
    import Ordered._

    val N: Int = end - start
    val incrementValues = increments(incrementFun)
      .takeWhile(p => p._1 < N / 3)
      .map(_._2)
      .reverse
      .toList

    logger.debug(s"Increments are $incrementValues")

    for (h <- incrementValues) {
      var i = 0
      while (i < h && i < N) {
        var j = h + i
        while (j < N) {
          val el = array(j)
          var k = j - h

          while (k >= 0 && array(k) > el) {
            array(k + h) = array(k)
            k = k - h
          }
          array(k + h) = el
          j = j + h
        }
        i = i + 1
      }
    }
  }

  private def increments(f: Int => Int): Stream[(Int, Int)] = {
    def incrementStream(h: Int): Stream[(Int, Int)] = {
      lazy val next = f(h)
      (h, next) #:: incrementStream(next)
    }
    (0, 1) #:: incrementStream(1)
  }

  private val incrementFun: Int => Int = h => 3 * h + 1
}
