import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.concurrent.TimeLimitedTests
import pl.pej.asd.graph._
import pl.pej.asd.graph.alg.{FordBellman, WarshalFloyd}
import org.scalatest.time.SpanSugar._

import scala.io.Source

abstract class AbstractPathSearchSuite(
                               graphs: Map[String, () => Graph[EmptySpec.type , IntWeight]],
                               searchAlgorithms: Map[String, (NodeId, NodeId, Graph[EmptySpec.type, IntWeight]) => (List[NodeId], Int)]
                               ) extends FunSuite {

  type TestGraph = Graph[EmptySpec.type, IntWeight]
  val data = Source.fromURL(getClass.getResource("graph.txt")).getLines().toList

  def loadData(graph: TestGraph): Unit = {
    for{
      i <- (0 until 1000).toList
    } {
      graph .addNode(NodeId(i), EmptySpec)
    }

    data.map { line =>
      val Array(from, to, weight) = line.split("; ")

      graph.addVertice(NodeId(from.toInt), NodeId(to.toInt), IntWeight(weight.toInt))
    }
  }

  for {
    (graphImplName, getGraph) <- graphs
    (searchImplName, search) <- searchAlgorithms
  } {
    test(s"$graphImplName on $searchImplName") {
      val graph = getGraph()
      loadData(graph)
      val (path, dist) = search(NodeId(109), NodeId(609), graph)

      assert(path === List(109, 713, 870, 614, 808, 609).map(NodeId))
      assert(dist === 18)
    }

  }

}


class PathSearchSuite extends AbstractPathSearchSuite(
  graphs = Map(
    "MatrixGraph" -> (() => new MatrixGraph[EmptySpec.type, IntWeight]()),
    "ListGraph" ->( () => new ListGraph[EmptySpec.type, IntWeight]())
  ),
  searchAlgorithms = Map(
    "WarshalFloyd" -> WarshalFloyd.findshortestPath _ ,
    "FordBellman" -> FordBellman.findshortestPath _
  )
)