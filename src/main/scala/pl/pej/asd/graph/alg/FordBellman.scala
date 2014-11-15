package pl.pej.asd.graph.alg

import pl.pej.asd.graph._


object FordBellman{

  import pl.pej.asd.graph.Graph._ //implicits

  def findshortestPath[NS<:NodeSpec, VS<:VerticeSpec](from: NodeId, to: NodeId, graph: Graph[NS,VS]): (List[NodeId], Int) = {

//    // Step 1: initialize graph
//    for each vertex v in vertices:

    val nodeIds = graph.nodeIds
    val idx: Map[NodeId, Int] = nodeIds.zipWithIndex.toMap


    val weightsArray = Array.fill(graph.nodeCount)(Int.MaxValue/2)
    val pathArray = Array.fill(graph.nodeCount)(-1)

    weightsArray(idx(from)) = 0

    def w(id: NodeId): Int = weightsArray(idx(id))
    def p(id: NodeId): Int = pathArray(idx(id))

    val indexes = (0 until graph.nodeCount).toList


    //    // Step 2: relax edges repeatedly
    //    for i from 1 to size(vertices)-1:
    //    for each edge (u, v) with weight w in edges:
    //    if weight[u] + w < weight[v]:
    //      weight[v] := weight[u] + w
    //    predecessor[v] := u
    for {
      i <- indexes
      Vertice(from, to, spec) <- graph.vertices
    } {
      if(w(from) + spec.weight < w(to)) {
        weightsArray(idx(to)) = w(from) + spec.weight
        pathArray(idx(to)) = idx(from)
      }
    }

//
//    // Step 3: check for negative-weight cycles
//    for each edge (u, v) with weight w in edges:
//    if weight[u] + w < weight[v]:
//      error "Graph contains a negative-weight cycle"
//    return weight[], predecessor[]

    for {
      Vertice(from, to, spec) <- graph.vertices
    } if(w(from) + spec.weight < w(to)){
      throw NegativeWeightCycle()
    }


    @scala.annotation.tailrec
    def readPath(from: Int, to: Int, acc: List[Int]): List[Int] = {
      if(from == to) acc
      else {
        readPath(from, pathArray(to), to :: acc)
      }
    }

    (from :: readPath(idx(from), idx(to), Nil).map(nodeIds(_)), w(to))
  }

  case class NegativeWeightCycle() extends RuntimeException("Graph contains a negative weight cycle")
}
