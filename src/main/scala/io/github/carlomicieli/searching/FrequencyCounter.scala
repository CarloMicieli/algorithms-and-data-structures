package io.github.carlomicieli.searching

import io.github.carlomicieli.dst.mutable.MinPQ

object FrequencyCounter {
  case class WordCount(word: String, count: Int)

  implicit val workCountOrd = new Ordering[WordCount] {
    def compare(x: WordCount, y: WordCount): Int = x.count.compare(y.count)
  }

  def apply(filename: String, minLength: Int)(st: SymbolTable[String, Int]): List[WordCount] = {
    val words = Words.fromFile(filename)
    for (w <- words if w.length() >= minLength) {
      if (st.contains(w))
        st(w) = st(w) + 1
      else
        st(w) = 1
    }

    val m = 10
    val minPQ = MinPQ[WordCount](m + 1)

    st.keys.foreach { k =>
      val wc = WordCount(k, st(k))
      minPQ.insert(wc)
      if (minPQ.size > m) {
        minPQ.deleteMin()
      }
    }

    minPQ.toList.sorted
  }
}
