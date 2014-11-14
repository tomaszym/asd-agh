package pl.pej.asd

package object graph {

  case class NodeExistsException(id: NodeId) extends RuntimeException(s"Node with id ${id.id} already exists")
  case class NodeDoesntExistException(id: NodeId) extends RuntimeException(s"Node with id ${id.id} doesn't exist")

  case class VerticeExistsException(fromId: NodeId, toId: NodeId) extends RuntimeException(s"Vertice binding nodes ${fromId.id} and ${toId.id} already exists")
  case class VerticeDoesntExistException(fromId: NodeId, toId: NodeId) extends RuntimeException(s"Vertice binding nodes ${fromId.id} and ${toId.id} doesn't exist")

  case class GraphFullException(size: Int) extends RuntimeException(s"Graph is full - already has $size nodes.")
}
