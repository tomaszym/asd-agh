package pl.pej.asd.graph

abstract class Graph[NS <: NodeSpec, VS <: VerticeSpec] {

  def nodes: List[Node[NS]]
  def nodeIds: List[NodeId]

  def vertices: List[Vertice[VS]]


  /** Adds new node to the graph.
   * @param id identifier of the node
   * @param ns node specification
   * @return true if id is not present yet, false otherwise
   */
  def addNode(id: NodeId, ns: NS)

  /** Removes node from graph.
   * @param id identifier of the node
   */
  def remNode(id: NodeId)

  /** Adds one-direction vertice to the graph. (Only if it hasn't existed yet.)
    *
    * @param idFrom id of the "from" node
    * @param idTo id of the "to" node
    * @param spec node specification
    */
  def addVertice(idFrom: NodeId, idTo: NodeId, spec: VS)

  /** Removes one direction vertice
    *
    * @param idFrom id of the from node
    * @param idTo id of the to node
    */
  def remVertice(idFrom: NodeId, idTo: NodeId)

  /** Returns neighbours of the node (in one-direction sense)
    *
    * @param n id of the node
    * @return
    */
  def neighboursOf(n: NodeId): List[NodeId]

  /** Returns true if there's a connection from 'from' node to 'to' node
    *
    * @param from id of the node from which we're looking
    * @param to
    * @return
    */
  def areNeighbours(from: NodeId, to: NodeId): Boolean = findVertice(from, to).isDefined

  /** Vertices outgoing from the node
    *
    * @param id node id of the node
    * @return list of vertices
    */
  def verticesOut(id: NodeId): List[Vertice[VS]]

  /** Vertices ingoing to the node
    *
    * @param id node id of the node
    * @return list of vertices
    */
  def verticesIn(id: NodeId): List[Vertice[VS]]

  /** Finds node by id if exists, None otherwise
    *
    * @param id node id
    * @return Some(Node) if such exists, None otherwise
    */
  def findNode(id: NodeId): Option[Node[NS]]

  /** Finds vertice from node `from` to node `to`.
    *
    * @param from id of the node from
    * @param to id of the node to
    * @return Some(vertice) or None
    */
  def findVertice(from: NodeId, to: NodeId): Option[Vertice[VS]]

  /** Count of the nodes in the graph
    *
    * @return int
    */
  def nodeCount: Int

  /** Count of the vertices in the graph
    * (a vertice is one-directional)
    *
    * @return
    */
  def verticeCount: Int

  /** Indicates wheter graph is empty (no nodes and therefore no vertices).
    *
    * @return
    */
  def isEmpty : Boolean = nodeCount == 0
}

object Graph {

  implicit def node2Int(id: NodeId): Int = id.id
}