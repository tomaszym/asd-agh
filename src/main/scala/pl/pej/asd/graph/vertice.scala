package pl.pej.asd.graph

case class Vertice[S <: VerticeSpec](from: NodeId, to: NodeId, spec: S)

abstract class VerticeSpec {
  def weight: Int
}
case class IntWeight(weight: Int) extends VerticeSpec

case class Flow(maxFlow: Int) extends VerticeSpec {
  def weight = maxFlow

  var flow: Int = 0
  def flowBack = maxFlow - flow
}
