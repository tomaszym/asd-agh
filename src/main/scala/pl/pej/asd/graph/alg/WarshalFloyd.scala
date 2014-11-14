package pl.pej.asd.graph.alg

import pl.pej.asd.graph.{VerticeSpec, NodeSpec, NodeId, Graph}


trait WarshalFloyd[NS <: NodeSpec, VS <: VerticeSpec] { this: Graph[NS, VS] =>

  import Graph._ //implicits

  def warshalFloyd(from: NodeId, to: NodeId): (List[NodeId], Int) = {

    val size = this.nodeCount
    val indexes = (0 until size).toList

    val weights: Array[Array[Int]] = Array.fill(size,size)(Int.MaxValue/2)
    for (i <- indexes) weights(i)(i) = 0

    val paths: Array[Array[Int]] = Array.fill(size,size)(-1)

    for {
      v <- vertices
    } {
      weights(v.from)(v.to) = v.spec.weight
      paths(v.from)(v.to) = v.from

    }

    for {
      u <- indexes
      v1 <- indexes
      v2 <- indexes
    } {
      if(weights(v1)(v2) > weights(v1)(u) + weights(u)(v2)){

        weights(v1)(v2) = weights(v1)(u) + weights(u)(v2)
        paths(v1)(v2) = paths(u)(v2)
      }
    }


    @scala.annotation.tailrec
    def readPath(from: Int, to: Int, acc: List[Int]): List[Int] = {
      if(from == to) acc
      else {
        readPath(from, paths(from)(to), to :: acc)
      }
    }


    ((from.id :: readPath(from.id, to.id, Nil)).map(NodeId), weights(from.id)(to.id))
  }

}
