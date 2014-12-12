import org.scalatest.FunSuite
import pl.pej.asd.graph._
import pl.pej.asd.graph.util.{ResidualVertice, ResidualNeighbour, FlowGraph}

import scala.io.Source

class FlowSuite extends FunSuite {

  implicit def int2node(i: Int): NodeId = NodeId(i)


  type TestGraph = Graph[EmptySpec.type, Flow] with FlowGraph[EmptySpec.type, Flow]
  val data = Source.fromURL(getClass.getResource("graph.txt")).getLines().toList

  def loadData(graph: TestGraph): Unit = {
    for{
      i <- (0 until 1000).toList
    } {
      graph .addNode(NodeId(i), EmptySpec)
    }

    data.map { line =>
      val Array(from, to, weight) = line.split("; ")

      graph.addVertice(NodeId(from.toInt), NodeId(to.toInt), Flow(weight.toInt))
    }
  }

  test("Flows and bfs in 2-node residual graph") {


    val fg = new ListGraph[EmptySpec.type, Flow]() with FlowGraph[EmptySpec.type, Flow]

    fg.addNode(1, EmptySpec)
    fg.addNode(2, EmptySpec)

    val vertice = Flow(5)
    vertice.flow = 3

    fg.addVertice(1,2, vertice)

    assert(fg.residualNeighbours(1) === List(ResidualNeighbour(2, 2)))
    assert(fg.residualNeighbours(2) === List(ResidualNeighbour(1, 3)))

    assert(FlowGraph.bfs(fg, 1,2) === List(ResidualVertice(1,2,2)))
    assert(FlowGraph.bfs(fg, 2,1) === List(ResidualVertice(2,1,3)))

  }
}
