package pl.pej.asd.graph

case class Vertice[S <: VerticeSpec](from: NodeId, to: NodeId, spec: S)

abstract class VerticeSpec
case class IntWeight(w: Int) extends VerticeSpec

