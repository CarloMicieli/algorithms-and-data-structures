package io.github.carlomicieli.sorting

import scala.reflect.ClassTag
import scala.util.Random

trait SampleData {

  def randomIntArray(size: Int): Array[Int] = {
    randomArray[Int](size)(i => i)
  }

  def randomArray[A: ClassTag](size: Int)(gen: Int => A): Array[A] = {
    val randomInts = stream(new Random())
    randomInts.take(size).map(gen).toArray
  }

  private def stream(rnd: Random): Stream[Int] = {
    rnd.nextInt() #:: stream(rnd)
  }

}