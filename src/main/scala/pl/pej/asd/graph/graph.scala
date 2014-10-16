package pl.pej.asd.graph

abstract class Graph[N <: Node] {
   
    def addNode(n: NodeId): Boolean
    def remNode(n: NodeId): Boolean
       
    def addVertice(v: Vertice): Boolean
    def remVertice(v: Vertice): Boolean
   
    def neighboursOf(n: NodeId): List[NodeId]
    def neighbours_?(a: NodeId, b: NodeId): Boolean
    def verticesOut(n: NodeId): List[Vertice]
    def verticesIn(n: NodeId): List[Vertice]
   
    def findNode(n: NodeId): N
    def findVertice(a: NodeId, b: NodeId): Vertice
   
    def nodes: Int
    def vertices: Int
    def empty_? : Boolean = (nodes == 0) && (vertices == 0)
}

class MatrixGraph extends Graph {

   
    val rep: Array[Array[Option[Vertice]]] = scala.collection.mutable.Array.tabulate(100,100)(None)
   
}

