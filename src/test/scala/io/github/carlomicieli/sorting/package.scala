package io.github.carlomicieli

package object sorting {
  implicit class ArrayOps(array: Array[Char]) {

    def isSorted: Boolean = {
      def unsorted(p: (Char, Char)): Boolean = {
        val (first, second) = p
        first > second
      }

      !array.zip(array.tail).exists(unsorted)
    }

    def asString: String = new String(array)
  }

}
