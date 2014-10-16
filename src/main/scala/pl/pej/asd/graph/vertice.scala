package pl.pej.asd.graph

sealed abstract class AbstractVertice {
  def from: NodeId
  def to: NodeId
}

case class Vertice(from: NodeId, to: NodeId, v: VerticeSpec) extends AbstractVertice

sealed abstract class VerticeSpec
case class IntWeight(w: Int) extends VerticeSpec

