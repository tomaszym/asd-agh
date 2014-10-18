package pl.pej.asd.graph

abstract class Graph[NS <: NodeSpec, VS <: VerticeSpec] {
   
    def addNode(id: NodeId, ns: NS): Boolean
    def remNode(id: NodeId): Boolean
       
    def addVertice(idFrom: NodeId, idTo: NodeId, spec: VS): Boolean
    def remVertice(idFrom: NodeId, idTo: NodeId): Boolean
   
    def neighboursOf(n: NodeId): List[NodeId]
    def areNeighbours(a: NodeId, b: NodeId): Boolean
    def verticesOut(n: NodeId): List[Vertice[VS]]
    def verticesIn(n: NodeId): List[Vertice[VS]]
   
    def findNode(n: NodeId): Node[NS]
    def findVertice(a: NodeId, b: NodeId): Vertice[VS]
   
    def nodeCount: Int
    def verticeCount: Int
    def isEmpty : Boolean = nodeCount == 0
}

class MatrixGraph[NS <: NodeSpec, VS <: VerticeSpec] extends Graph[NS,VS] {

  protected val nodes: scala.collection.mutable.AbstractMap[NodeId, Int] = scala.collection.mutable.HashMap()

  protected val rep: Array[Array[Option[Vertice[VS]]]] = Array.fill(100,100)(None)

  override def addNode(id: NodeId, ns: NS): Boolean = ???

  override def areNeighbours(a: NodeId, b: NodeId): Boolean = ???

  override def verticesIn(n: NodeId): List[Vertice[VS]] = ???

  override def neighboursOf(n: NodeId): List[NodeId] = ???

  override def verticeCount: Int = ???

  override def findVertice(a: NodeId, b: NodeId): Vertice[VS] = ???

  override def nodeCount: Int = ???

  override def findNode(n: NodeId): Node[NS] = ???

  override def addVertice(idFrom: NodeId, idTo: NodeId, spec: VS): Boolean = ???

  override def remVertice(idFrom: NodeId, idTo: NodeId): Boolean = ???

  override def remNode(id: NodeId): Boolean = ???

  override def verticesOut(n: NodeId): List[Vertice[VS]] = ???
}

class ListGraph[NS <: NodeSpec, VS <: VerticeSpec] extends Graph[NS,VS] {
  override def addNode(id: NodeId, ns: NS): Boolean = ???

  override def areNeighbours(a: NodeId, b: NodeId): Boolean = ???

  override def verticesIn(n: NodeId): List[Vertice[VS]] = ???

  override def neighboursOf(n: NodeId): List[NodeId] = ???

  override def verticeCount: Int = ???

  override def findVertice(a: NodeId, b: NodeId): Vertice[VS] = ???

  override def nodeCount: Int = ???

  override def findNode(n: NodeId): Node[NS] = ???

  override def addVertice(idFrom: NodeId, idTo: NodeId, spec: VS): Boolean = ???

  override def remVertice(idFrom: NodeId, idTo: NodeId): Boolean = ???

  override def remNode(id: NodeId): Boolean = ???

  override def verticesOut(n: NodeId): List[Vertice[VS]] = ???
}



