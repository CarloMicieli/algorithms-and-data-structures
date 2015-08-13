package io.github.carlomicieli

import com.typesafe.scalalogging.LazyLogging
import io.github.carlomicieli.searching.BinarySearchST

object MyApp extends LazyLogging {
  def main(args: Array[String]): Unit = {

    val st = BinarySearchST[String, Int](16)
    st("hello") = 1
    println(st.toString)



    /*
    val st = SequentialSearchST.empty[String, Int]

    val dur = timed { () => {
        val output = FrequencyCounter("/data/tale.txt", 8)(st)
        println(output.mkString("\n"))
      }
    }

    println(dur + " seconds")
    */
  }

  def timed[U](op: () => U): Long = {
    import java.time._
    import java.time.temporal._
    val start: Instant = Instant.now()
    op()
    val end: Instant = Instant.now()

    start.until(end, ChronoUnit.SECONDS)
  }
}
