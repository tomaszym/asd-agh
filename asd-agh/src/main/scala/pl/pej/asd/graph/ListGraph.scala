package pl.pej.asd.graph


class ListGraph[NS <: NodeSpec, VS <: VerticeSpec] extends Graph[NS,VS] {

  protected val nodesMap = scala.collection.mutable.HashMap[NodeId, NS]()
  protected val vertsList = scala.collection.mutable.ListBuffer[Vertice[VS]]()

  /** Adds new node to the graph.
    * @param id identifier of the node
    * @param ns node specification
    * @return true if id is not present yet, false otherwise
    */
  override def addNode(id: NodeId, ns: NS): Unit = {
    if(nodesMap.get(id).isDefined) {
      throw NodeExistsException(id)
    }
    nodesMap.update(id, ns)
  }

  /** Vertices ingoing to the node
    *
    * @param id node id of the node
    * @return list of vertices
    */
  override def verticesIn(id: NodeId): List[Vertice[VS]] = {
    vertsList.filter { v =>
      v.to == id
    }.toList
  }

  /** Vertices outgoing from the node
    *
    * @param id node id of the node
    * @return list of vertices
    */
  override def verticesOut(id: NodeId): List[Vertice[VS]] = {
    vertsList.filter { v =>
      v.from == id
    }.toList
  }
  /** Returns neighbours of the node (in one-direction sense)
    *
    * @param n id of the node
    * @return
    */
  override def neighboursOf(n: NodeId): List[NodeId] = {
    vertsList.filter { v =>
      v.from == n
    }.map(_.to).toList
  }

  /** Count of the vertices in the graph
    * (a vertice is one-directional)
    *
    * @return
    */
  override def verticeCount: Int = vertsList.size

  /** Finds vertice from node `from` to node `to`.
    *
    * @param from id of the node from
    * @param to id of the node to
    * @return Some(vertice) or None
    */
  override def findVertice(from: NodeId, to: NodeId): Option[Vertice[VS]] = vertsList.find{ v => v.from == from && v.to == to}

  /** Count of the nodes in the graph
    *
    * @return int
    */
  override def nodeCount: Int = nodesMap.size

  /** Finds node by id if exists, None otherwise
    *
    * @param id node id
    * @return Some(Node) if such exists, None otherwise
    */
  override def findNode(id: NodeId): Option[Node[NS]] = nodesMap.get(id).map( Node(id, _))

  /** Adds one-direction vertice to the graph. (Only if it hasn't existed yet.)
    *
    * @param idFrom id of the "from" node
    * @param idTo id of the "to" node
    * @param spec node specification
    */
  override def addVertice(idFrom: NodeId, idTo: NodeId, spec: VS): Unit = {
    vertsList += Vertice(idFrom, idTo, spec)
  }

  /** Removes one direction vertice
    *
    * @param idFrom id of the from node
    * @param idTo id of the to node
    */
  override def remVertice(idFrom: NodeId, idTo: NodeId): Unit = {
    val v = findVertice(idFrom, idTo)
    v match {
      case None => throw VerticeDoesntExistException(idFrom, idTo)
      case Some(vert) => vertsList.-=(vert)
    }
  }

  /** Removes node from graph.
    * @param id identifier of the node
    */
  override def remNode(id: NodeId): Unit = {
    if(nodesMap.remove(id).isEmpty) throw NodeDoesntExistException(id)
  }

  override def nodes: List[Node[NS]] = nodesMap map { case (id, spec) => Node(id,spec)} toList

  override def nodeIds: List[NodeId] = nodesMap map { case (id, _) => id} toList

  override def vertices: List[Vertice[VS]] = vertsList.toList
}