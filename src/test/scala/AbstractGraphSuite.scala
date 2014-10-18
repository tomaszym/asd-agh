import org.scalatest.FunSuite
import pl.pej.asd.graph._

case object MockVerticeSpec extends VerticeSpec
case object MockNodeSpec extends NodeSpec

abstract class AbstractGraphSuite(graph: () => Graph[MockNodeSpec.type, MockVerticeSpec.type]) extends FunSuite {

  implicit def int2node(i: Int): NodeId = NodeId(i)


  test("new graph is empty") {
    val g = graph()

    assert(g.isEmpty === true)
  }

  test("graph with one node is not empty and of size one") {
    val g = graph()
    g.addNode(NodeId(0), MockNodeSpec)
    assert(g.isEmpty === false)
    assert(g.nodeCount === 1)
  }

  test("graph with two nodes and a vertice return info corectly") {
    val g = graph()

    g.addNode(NodeId(0), MockNodeSpec)
    g.addNode(NodeId(1), MockNodeSpec)

    g.addVertice(0,1, MockVerticeSpec)

    assert(g.areNeighbours(0, 1) === true)
    assert(g.neighboursOf(0) === List(NodeId(1)))
    assert(g.neighboursOf(1) === Nil)
    assert(g.verticesIn(1) === List(Vertice(0,1, MockVerticeSpec)))
    assert(g.verticesIn(0) === Nil)
    assert(g.verticesOut(0) === List(Vertice(0,1, MockVerticeSpec)))
    assert(g.verticesOut(1) === Nil)
  }
}


class ListGraphSuite extends AbstractGraphSuite(() => new ListGraph[MockNodeSpec.type, MockVerticeSpec.type])
class MatrixGraphSuite extends AbstractGraphSuite(() => new MatrixGraph[MockNodeSpec.type, MockVerticeSpec.type])