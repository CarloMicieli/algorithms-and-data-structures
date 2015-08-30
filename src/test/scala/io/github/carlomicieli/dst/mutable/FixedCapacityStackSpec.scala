package io.github.carlomicieli.dst.mutable

import io.github.carlomicieli.test.AbstractTestSpec
import io.github.carlomicieli.util.{Good}
import org.scalatest.{Matchers, FlatSpec}

class FixedCapacityStackSpec extends AbstractTestSpec with SampleFixedCapacityStacks {

  "A fixed capacity stack" should "has size equal 0 if empty" in {
    val empty = Stack.fixed(16)
    empty.size shouldBe 0
    empty.isEmpty shouldBe true
  }

  "Push an element in a fixed capacity stack" should "change its size" in {
    val st = stack(16)
    st.push(1)
    st.push(2)
    st.size shouldBe 2

    st.top shouldBe Some(2)
    st.isEmpty shouldBe false
    st.size shouldBe 2
  }

  "Push an element into a fixed capacity stack" should "throw an exception if full" in {
    val st = stack(2)
    st.push(1)
    st.push(2)

    val res = intercept[FullStackException] {
      st.push(-1)
    }
  }

  "Pop an element out of a fixed capacity stack" should "throw an exception if empty" in {
    val st = stack(2)

    val res = intercept[EmptyStackException] {
      st.pop()
    }
  }

  "Pop an element out of a fixed capacity stack" should "work in LIFO fashion" in {
    val st = stack(2)
    st.push(1)
    st.push(2)

    val k = st.pop()
    k shouldBe 2
  }

  "peek" should "return the top element on the stack, without changing it" in {
    val st = stack(2)
    st.push(1)

    st.top shouldBe Some(1)
    st.size shouldBe 1
  }
}

trait SampleFixedCapacityStacks {
  def stack(n: Int): Stack[Int] = {
    Stack.fixed[Int](n)
  }
}
