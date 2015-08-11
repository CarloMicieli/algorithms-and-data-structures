package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.dst.{Stack}
import io.github.carlomicieli.util.{Good}
import org.scalatest.{Matchers, FlatSpec}

class FixedCapacityStackSpec extends FlatSpec with Matchers with SampleFixedCapacityStacks {

  "A fixed capacity stack" should "has size equal 0 if empty" in {
    val empty = FixedCapacityStack(16)
    empty.size should be(0)
    empty.isEmpty should be(true)
  }

  "Push an element in a fixed capacity stack" should "change its size" in {
    val st = stack(16)
    st.push(1)
    st.push(2)
    st.size should be(2)

    st.peek.get should be(2)
    st.isEmpty should be(false)
    st.size should be(2)
  }

  "Push an element into a fixed capacity stack" should "return a Bad value if full" in {
    val st = stack(2)
    st.push(1)
    st.push(2)

    val res = st.push(-1)
    res.isBad should be(true)
  }

  "Pop an element out of a fixed capacity stack" should "return a Bad value if empty" in {
    val st = stack(2)
    val res = st.pop()
    res.isBad should be(true)
  }

  "Pop an element out of a fixed capacity stack" should "work in LIFO fashion" in {
    val st = stack(2)
    st.push(1)
    st.push(2)

    val Good((k, _)) = st.pop()
    k should be(2)
  }

  "peek" should "return the top element on the stack, without changing it" in {
    val st = stack(2)
    st.push(1)

    st.peek.get should be(1)
    st.size should be(1)
  }
}

trait SampleFixedCapacityStacks {
  def stack(n: Int): Stack[Int] = {
    FixedCapacityStack[Int](n)
  }
}
