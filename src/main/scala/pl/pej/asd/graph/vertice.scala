package pl.pej.asd.graph

case class Vertice[S <: VerticeSpec](from: NodeId, to: NodeId, spec: S)

abstract class VerticeSpec {
  def weight: Int
}
case class IntWeight(weight: Int) extends VerticeSpec

