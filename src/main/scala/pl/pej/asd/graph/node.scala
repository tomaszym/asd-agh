package pl.pej.asd.graph


case class NodeId(id: Int) extends AnyVal

case class Node[S <: NodeSpec](id: NodeId, spec: S)

abstract class NodeSpec
case object EmptySpec extends NodeSpec

