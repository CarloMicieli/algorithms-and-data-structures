/*
 * Copyright 2014 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the 'License');
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an 'AS IS' BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.carlomicieli

import java.nio.file.{Paths, StandardOpenOption, Files}
import com.carlomicieli.algo.Arrays

import com.carlomicieli.algo.sorting.{InsertionSort, SelectionSort, BubbleSort, ProblemSet}

/**
 * @author Carlo Micieli
 */
object MyApp extends App {

/*
  val numbers = (0 to 524288).toArray
  Arrays.shuffle(numbers)

  val bytes = numbers.take(262144).map(_.toString).mkString("\n").getBytes

  Files.write(Paths.get("/home/carlo/Projects/hello-scala/src/main/resources/test-data/", "set262144.txt"), bytes,
    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

*/





  val report = ProblemSet.solveAll(new InsertionSort)


}


