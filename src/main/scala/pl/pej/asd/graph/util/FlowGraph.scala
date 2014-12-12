package pl.pej.asd.graph.util

import pl.pej.asd.graph._

case class ResidualNeighbour(id: NodeId, value: Int)
case class ResidualVertice(from: NodeId, to: NodeId, value: Int)

trait FlowGraph [NS <: NodeSpec, VS <: Flow]{ this: Graph[NS, VS] =>


  def residualNeighbours(id: NodeId): List[ResidualNeighbour] = {

    // residual flow, when you can push something more through vertice TO a node
    val residualVerticesOut = verticesOut(id).filter(v => v.spec.flow < v.spec.maxFlow).map(v => (v.to, v.spec.maxFlow - v.spec.flow))
    // residual flow when you can push less FROM a vertice
    val residualVerticesTo = verticesIn(id).filter(v => v.spec.flow > 0).map(v => (v.from, v.spec.flow))

    // merged
    val residualVertices = (residualVerticesOut ++ residualVerticesTo).groupBy{ case (i, _) => i}.map { case (i, vertices) =>
      ResidualNeighbour(i, vertices.map(_._2).sum)
    }.toList

    residualVertices
  }

}

object FlowGraph {

  def bfs[NS <: NodeSpec, VS <: Flow](fg: FlowGraph[NS, VS], source: NodeId, destination: NodeId):List[ResidualVertice] = {


    val visited: scala.collection.mutable.BitSet = scala.collection.mutable.BitSet()

    val prev: scala.collection.mutable.Map[NodeId, ResidualVertice] = scala.collection.mutable.HashMap()

    val queue: scala.collection.mutable.Queue[NodeId] = scala.collection.mutable.Queue()

    queue.enqueue(source) //1

    var found = false

    while (!found && queue.nonEmpty) { // main loop

      val from = queue.dequeue() //1
      val neighbours = fg.residualNeighbours(from).filterNot{ n =>
        visited.contains(n.id.id)
      }

      neighbours.foreach { n=> // loop over neighbours of one node

        queue.enqueue(n.id)

        visited.+=(n.id.id)

        prev.update(n.id, ResidualVertice(from, n.id, n.value)) // 1-> residual(2, 3)

        if(n.id == destination) {
          found = true
        }
      }
    }

    @scala.annotation.tailrec
    def constructPath(current: NodeId, path: List[ResidualVertice]): List[ResidualVertice] = {
      if(current == source) path
      else {
        val p = prev(current)
        constructPath(p.from, p :: path)
      }
    }


    if(found)
      constructPath(destination, List())
    else
      List()
  }
}