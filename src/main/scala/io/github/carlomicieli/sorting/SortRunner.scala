package io.github.carlomicieli.sorting

import com.typesafe.scalalogging.LazyLogging

object SortRunner extends SampleData with LazyLogging {

  private type TestCase = (Sorting, Array[Int])
  private type TestCases = Seq[TestCase]

  def apply(problemSize: Int, sortAlgorithm: Sorting): TestResult = {
    val test = (sortAlgorithm, randomIntArray(problemSize))
    runTest(test)
  }

  def apply(problemSizes: Seq[Int], sortAlgorithms: Seq[Sorting]): Seq[TestResult] = {
    testCases(problemSizes, sortAlgorithms).map(runTest)
  }

  private def runTest(t: TestCase): TestResult = {
    val (algo, array) = t
    TestResult(algo.name, withLogging(algo, array), array.length)
  }

  // TRIVIAL IMPLEMENTATION: NOT a real micro-benchmark!!!
  private def timed[U](op: => U): Long = {
    import java.time._
    import java.time.temporal._
    val start: Instant = Instant.now()
    op
    val end: Instant = Instant.now()

    start.until(end, ChronoUnit.SECONDS)
  }

  private def withLogging(sorting: Sorting, arr: Array[Int]): Long = {
    timed {
      logger.debug(s"*Before* ${sorting.printArray(arr)}")
      sorting.sort(arr)
      logger.debug(s"Array is now sorted? ${sorting.isSorted(arr)}")
      logger.debug(s"*After*  ${sorting.printArray(arr)}")
    }
  }

  private def testCases(sizes: Seq[Int], algorithms: Seq[Sorting]): TestCases = {
    for {
      algo <- algorithms
      size <- sizes
    } yield (algo, randomIntArray(size))
  }
}

case class TestResult(algorithmName: String, runTime: Long, size: Int)