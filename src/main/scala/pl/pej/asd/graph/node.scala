package pl.pej.asd.graph


class NodeId(val id: Int) extends AnyVal

sealed abstract class AbstractNode{
  def id: NodeId
}

case class Node(id: NodeId, spec: NodeSpec) extends AbstractNode

sealed abstract class NodeSpec
case object EmptySpec extends NodeSpec

