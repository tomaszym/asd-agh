import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.concurrent.TimeLimitedTests
import pl.pej.asd.graph._
import pl.pej.asd.graph.alg.WarshalFloyd
import org.scalatest.time.SpanSugar._

import scala.io.Source

class AbstractPathSearchSuite extends FunSuite with TimeLimitedTests with BeforeAndAfter {

  val timeLimit = 10.seconds

  val listGraph = new ListGraph[EmptySpec.type , IntWeight]() with WarshalFloyd[EmptySpec.type, IntWeight]
  val matrixGraph = new MatrixGraph[EmptySpec.type , IntWeight]() with WarshalFloyd[EmptySpec.type, IntWeight]

  val data = Source.fromURL(getClass.getResource("graph.txt"))
  for{
    i <- (0 until 1000).toList
  } {
    listGraph.addNode(NodeId(i), EmptySpec)
    matrixGraph.addNode(NodeId(i), EmptySpec)
  }

  data.getLines().toList.map { line =>
    val Array(from, to, weight) = line.split("; ")

    listGraph.addVertice(NodeId(from.toInt), NodeId(to.toInt), IntWeight(weight.toInt))
    matrixGraph.addVertice(NodeId(from.toInt), NodeId(to.toInt), IntWeight(weight.toInt))
  }

  test("ListGraph WarshalFloyd") {


    val (path, dist) = listGraph.warshalFloyd(NodeId(109), NodeId(609))

    assert(path === List(109, 713, 870, 614, 808, 609).map(NodeId))
  }

  test("MatrixGraph WarshalFloyd") {

    val (path, dist) = matrixGraph.warshalFloyd(NodeId(109), NodeId(609))

    assert(path === List(109, 713, 870, 614, 808, 609).map(NodeId))
  }

}
