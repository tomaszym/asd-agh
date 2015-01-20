package pl.pej.asd.js

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.HTMLElement
import pl.pej.asd.geometric.Point

case class PointDisplay(points: List[Point], offset: Double = 20) {

  private case class Pos(x: Double, y: Double)

  // Create the canvas
  private[this] val canvas = document.createElement("canvas").asInstanceOf[dom.HTMLCanvasElement]
  canvas.setAttribute("id", "point-display")
  private[this] val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  canvas.width = (0.75 * dom.window.innerWidth).toInt
  canvas.height = (0.95 * dom.window.innerHeight - 100).toInt

  private[this] val minX = points.minBy(_.x).x
  private[this] val maxX = points.maxBy(_.x).x
  private[this] val scaleX = (maxX - minX + 2*offset)/canvas.width.toDouble

  private[this] val minY = points.minBy(_.y).y
  private[this] val maxY = points.maxBy(_.y).y
  private[this] val scaleY = (maxY - minY + 2*offset)/canvas.height.toDouble

  points.foreach { p =>
    drawPoint(p)
  }


  private[this] def scaledPos(p: Point): Pos = {
    Pos(
      x = minX + p.x/scaleX,
      y = minY + p.y/scaleY
    )
  }


  private[this] def drawPoint(p: Point) = {
    val Pos(x, y) = scaledPos(p)
    ctx.strokeRect(x, y, 5,5)
  }



  def htmlElement: HTMLElement = canvas
}
