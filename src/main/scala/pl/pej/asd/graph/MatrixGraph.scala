package pl.pej.asd.graph

import pl.pej.asd.graph.util.FullBitSetUtil


/** Allowed assumption for the project: maximum node number.
  * This makes this lib unusable for any reasonable practical purpose, sorry. :-)
  *
  * @param arraySize maximal node count
  * @tparam NS information, which each node holds
  * @tparam VS information which each vertice holds (usually some kind of weight)
  */
class MatrixGraph[NS <: NodeSpec, VS <: VerticeSpec](arraySize: Int = 1100) extends Graph[NS,VS] with FullBitSetUtil {

  protected val id2nodeMap: scala.collection.mutable.AbstractMap[NodeId, (Int, NS)] = scala.collection.mutable.HashMap()

  protected val verticesRep: Array[Array[Option[Vertice[VS]]]] = Array.fill(arraySize,arraySize)(None)

  /** Size of the set is determined by the formula pow2(ceil(log2(arraySize)))
    *
    *
    */
  protected val posSet: scala.collection.mutable.BitSet = scala.collection.mutable.BitSet.fromBitMaskNoCopy(fullSetBitMask(arraySize))
  /** Helper function which returns position in 2-dim array representation.
    *
    * @param id
    * @return
    */
  protected def nodePos(id: NodeId): Int = {
    id2nodeMap.get(id) match {
      case Some((pos, _)) => pos
      case None => throw NodeDoesntExistException(id)
    }
  }


  /** Adds new node to the graph.
    * @param id identifier of the node
    * @param ns node specification
    * @return true if id is not present yet, false otherwise
    */
  override def addNode(id: NodeId, ns: NS): Unit = {
    if(id2nodeMap.get(id).isDefined) throw NodeExistsException(id)

    val pos = posSet.firstKey
    posSet.remove(pos)



    id2nodeMap.update(id, (pos, ns)) // TODO set ze zbiorem wolnych idkow
  }

  /** Returns neighbours of the node (in one-direction sense)
    * so the nodes to which there is a connection from the given id.
    *
    * @param n id of the node
    * @return
    */
  override def neighboursOf(n: NodeId): List[NodeId] = {
    verticesRep(nodePos(n)).flatten.toList.map(_.to)
  }

  /** Finds vertice from node `from` to node `to`.
    *
    * @param from id of the node from
    * @param to id of the node to
    * @return Some(vertice) or None
    */
  override def findVertice(from: NodeId, to: NodeId): Option[Vertice[VS]] = {
    val fromPos = nodePos(from)
    val toPos = nodePos(to)

    verticesRep(fromPos)(toPos)
  }

  /** Finds node by id if exists, None otherwise
    *
    * @param id node id
    * @return Some(Node) if such exists, None otherwise
    */
  override def findNode(id: NodeId): Option[Node[NS]] = {
    id2nodeMap.get(id).map{ case (_, ns) => Node(id, ns)}
  }

  /** Adds one-direction vertice to the graph. (Only if it hasn't existed yet.)
    *
    * @param idFrom id of the "from" node
    * @param idTo id of the "to" node
    * @param spec node specification
    */
  override def addVertice(idFrom: NodeId, idTo: NodeId, spec: VS): Unit = {
    val fromPos = nodePos(idFrom)
    val toPos = nodePos(idTo)

    if(verticesRep(fromPos)(toPos).isDefined) throw VerticeExistsException(idFrom, idTo)
    verticesRep(fromPos)(toPos) = Some(Vertice(idFrom, idTo, spec))
  }

  /** Removes one direction vertice
    *
    * @param idFrom id of the from node
    * @param idTo id of the to node
    */
  override def remVertice(idFrom: NodeId, idTo: NodeId): Unit = {
    val fromPos = nodePos(idFrom)
    val toPos = nodePos(idTo)

    if(verticesRep(fromPos)(toPos).isEmpty) throw VerticeDoesntExistException(idFrom, idTo)
    verticesRep(fromPos)(toPos) = None
  }

  /** Vertices outgoing from the node
    *
    * @param id node id of the node
    * @return list of vertices
    */
  override def verticesOut(id: NodeId): List[Vertice[VS]] = {
    val pos = nodePos(id)

    verticesRep(pos).flatten.toList
  }

  /** Vertices ingoing to the node
    *
    * @param id node id of the node
    * @return list of vertices
    */
  override def verticesIn(id: NodeId): List[Vertice[VS]] = {
    val pos = nodePos(id)

    verticesRep.map(_(pos)).flatten.toList
  }

  /** Removes node from graph.
    * @param id identifier of the node
    */
  override def remNode(id: NodeId): Unit = {
    val pos = nodePos(id)

    id2nodeMap.remove(id)
    verticesRep(pos) = Array.fill[Option[Vertice[VS]]](arraySize)(None)
    for {
      i <- (0 until arraySize).toList
    } {
      verticesRep(i)(pos) = None
    }
    posSet.add(pos)
  }

  /** Count of the nodes in the graph
    *
    * @return int
    */
  override def nodeCount: Int = id2nodeMap.size

  /** Count of the vertices in the graph
    * (a vertice is one-directional)
    *
    * @return
    */
  override def verticeCount: Int = verticesRep.flatten.flatten.size

  override def nodes: List[Node[NS]] = id2nodeMap.map { case (id, (_, spec)) =>
    Node(id, spec)
  }.toList

  override def nodeIds: List[NodeId] = nodes.map(_.id)

  override def vertices: List[Vertice[VS]] = {
    verticesRep.flatMap{ _.filter( _.isDefined).map(_.get)}.toList
  }
}