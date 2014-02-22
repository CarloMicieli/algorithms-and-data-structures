package io.github.carlomicieli

import org.scalatest.FunSpec
import UnionFind._

class UnionFindSpecs extends FunSpec {
  describe("Quick find data structure") {

    it("should return its length") {
      val quf = quickFind(6)
      assert(quf.count === 6)
    }

    it("should validate input when two nodes are connected") {
      val quf = quickFind(6)
      intercept[IllegalArgumentException] {
        quf.union(6, 7)
      }
    }

    it("should connect two nodes") {
      val quf = quickFind(6)
      quf.union(1, 5)
      assert(quf.connected(1, 5) === true)
    }

    it("should connect three nodes") {
      val quf = quickFind(6)
      quf.union(1, 5)
      quf.union(3, 5)

      assert(quf.connected(1, 3) === true)
    }

    it("should connect more nodes") {
      val quf = quickFind(6)
      quf.union(1, 5)
      quf.union(2, 3)
      quf.union(3, 4)
      quf.union(0, 4)

      assert(quf.connected(1, 0) === false)
      assert(quf.connected(0, 2) === true)
    }

    it("should always return true when checking if a node is connected with itself") {
      val quf = quickFind(6)
      assert(quf.connected(1, 1) === true)
    }

    it("should validate input when checking nodes connections") {
      val quf = quickFind(6)
      intercept[IllegalArgumentException] {
        quf.connected(6, 7)
      }
    }

    it("should check whether two nodes are not connected") {
      val quf = quickFind(6)
      assert(quf.connected(0, 5) === false)
    }

    it("should find a node id") {
      val quf = quickFind(7)
      quf.union(1, 2)
      quf.union(4, 5)
      quf.union(6, 4)
      assert(quf.find(4) === 6)
      assert(quf.find(2) === 1)
      assert(quf.find(3) === 3)
    }
  }

  describe("Quick union data structure") {
    it("should get the items count") {
      val qu = quickUnion(6)
      assert(qu.count === 6)
    }

    it("should check whether two nodes are not connected") {
      val qu = quickUnion(6)
      assert(qu.connected(1, 4) === false)
    }

    it("should check whether a node is connected with itself") {
      val qu = quickUnion(6)
      assert(qu.connected(1, 1) === true)
    }

    it("should merge two nodes with union") {
      val qu = quickUnion(6)
      qu.union(1, 4)
      assert(qu.connected(1, 4) === true)
    }

    it("should merge more nodes with union") {
      val quf = quickUnion(6)
      quf.union(1, 5)
      quf.union(2, 3)
      quf.union(3, 4)
      quf.union(0, 4)

      withClue("1 and 0 must not be connected: ") {
        assert(quf.connected(1, 0) === false)
      }
      withClue("0 and 4 must be connected: ") {
        assert(quf.connected(0, 4) === true)
      }
      withClue("0 and 2 must be connected: ") {
        assert(quf.connected(0, 2) === true)
      }
    }

    it("should find element root") {
      val quf = quickUnion(6)
      quf.union(1, 5)
      quf.union(2, 3)
      quf.union(3, 4)
      quf.union(0, 4)

      assert(quf.find(5) === 5)
      withClue("2 must have root 4: ") {
        assert(quf.find(2) === 4)
      }
    }

  }
}