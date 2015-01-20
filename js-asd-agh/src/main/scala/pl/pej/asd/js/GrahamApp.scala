package pl.pej.asd.js

import org.scalajs.dom
import org.scalajs.dom.{Event, document, HTMLElement}
import pl.pej.asd.geometric.Point

import scala.scalajs.js.JSApp

object GrahamApp extends JSApp {
  def main(): Unit = {
    println()

    val pointsArea = document.getElementById("points")
    pointsArea.onchange = { e: Event =>
      val text = pointsArea.valueOf().asInstanceOf[String]

      val points = parsePoints(text)

      val display = new PointDisplay(points)

      val oldDisplay = document.getElementById("points-display")
      if(oldDisplay != null) {
        dom.document.body.removeChild(oldDisplay)
      }

      dom.document.body.appendChild(display.htmlElement)

    }



  }

  def parsePoints(text: String): List[Point] = {

    List(Point(30,30), Point(0, 0), Point(100, 100))
  }
}
