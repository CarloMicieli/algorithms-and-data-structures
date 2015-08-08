package io.github.carlomicieli

import com.typesafe.scalalogging.LazyLogging
import io.github.carlomicieli.sorting._

object MyApp extends LazyLogging {
  def main(args: Array[String]): Unit = {

    val array = "QUICKSORTEXAMPLE".toCharArray

    logger.info(Shell.printArray(array))
    Quick.sort(array)
    logger.info(Shell.printArray(array))


    //val TestResult(_, runTime, _) = SortRunner(32000, Bubble)
    //logger.info(s"Completed in $runTime seconds")
  }
}
