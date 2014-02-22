package io.github.carlomicieli

import org.scalatest.FunSpec

class UnionFindSpecs extends FunSpec {
  describe("A quick find") {

    it("should return its length") {
      val quf = UnionFind(6)
      assert(quf.count === 6)
    }

    it("should validate input when two nodes are connected") {
      val quf = UnionFind(6)
      intercept[IllegalArgumentException] {
        quf.union(6, 7)
      }
    }

    it("should connect two nodes") {
      val quf = UnionFind(6)
      quf.union(1, 5)
      assert(quf.connected(1, 5) === true)
    }

    it("should connect three nodes") {
      val quf = UnionFind(6)
      quf.union(1, 5)
      quf.union(3, 5)

      assert(quf.connected(1, 3) === true)
    }

    it("should connect more nodes") {
      val quf = UnionFind(6)
      quf.union(1, 5)
      quf.union(2, 3)
      quf.union(3, 4)
      quf.union(0, 4)

      assert(quf.connected(1, 0) === false)
      assert(quf.connected(0, 2) === true)
    }

    it("should always return true when checking if a node is connected with itself") {
      val quf = UnionFind(6)
      assert(quf.connected(1, 1) === true)
    }

    it("should validate input when checking nodes connections") {
      val quf = UnionFind(6)
      intercept[IllegalArgumentException] {
        quf.connected(6, 7)
      }
    }

    it("should check whether two nodes are not connected") {
      val quf = UnionFind(6)
      assert(quf.connected(0, 5) === false)
    }

    it("should find a node id") {
      val quf = UnionFind(7)
      quf.union(1, 2)
      quf.union(4, 5)
      quf.union(6, 4)
      assert(quf.find(4) === 6)
      assert(quf.find(2) === 1)
      assert(quf.find(3) === 3)
    }
  }
}