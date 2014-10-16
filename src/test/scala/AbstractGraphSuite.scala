import org.scalatest.FunSuite
import pl.pej.asd.graph.Graph


abstract class AbstractGraphSuite(graph: () => Graph[_]) extends FunSuite {


  test("new graph is empty") {
    val g = graph()

    g.




  }

}
