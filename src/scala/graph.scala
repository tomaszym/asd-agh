
object HelloWorld {
   def main(args: Array[String]) {
      println("Hello, world!")
   }
}

class NodeId(val id: Int) extends AnyVal
// class VerticeId(val id: Int) extends AnyVal
class Weight(val w: Int) extends AnyVal

abstract class Node {
    def id: NodeId
}

case class Vertice(from: NodeId, to: NodeId, v: Vertice)
case class Vertice(weight: Weight)


abstract class Graph {
   
    def addNode(n: NodeId): Boolean
    def remNode(n: NodeId): Boolean
       
    def addVertice(v: Vertice): Boolean
    def remVertice(v: Vertice): Boolean
   
    def neighboursOf(n: NodeId): List[NodeId]
    def neighbours_?(a: NodeId, b: NodeId): Boolean
    def verticesOut(n: NodeId): List[Vertice]
    def verticesIn(n: NodeId): List[Vertice]
   
    def findNode(n: NodeId): Node
    def findVertice(a: NodeId, b: NodeId): Vertice
   
    def nodes: Int
    def vertices: Int
   
}

class MatrixGraph extends Graph {
   
    val rep: Array[Array[Option[Vertice]]] = scala.collection.mutable.Array.tabulate(100,100)(None)
   
}

