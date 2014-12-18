package pl.pej.asd.graph.alg

import pl.pej.asd.graph.util.{FlowGraph, ResidualVertice}
import pl.pej.asd.graph._

object FordFulkerson {

  def maxFlow[NS<:NodeSpec](source: NodeId, destination: NodeId, graph: Graph[NS,Flow] with FlowGraph[NS,Flow] ): Int = {

    var path: List[ResidualVertice] = FlowGraph.bfs(graph, source, destination)

    while(path.nonEmpty) {

      val change = path.minBy(_.value).value

      path.map { v =>


        val reverseFlow: Int = graph.findVertice(v.from, v.to).get.spec.plus(change)

        if(reverseFlow > 0)
          graph.findVertice(v.to, v.from).get.spec.minus(-reverseFlow)


      }
      path = FlowGraph.bfs(graph, source, destination)
//      println(path)
    }



    graph.verticesOut(source).map(_.spec.flow).sum
  }

}
