package pl.pej.asd.geometric

import math.atan2

object Graham {
  private type Points = List[Point]

  def scan(points: Points): Points = {

    val min: Point = points.minBy(_.y)
    val sorted: Points = points.sortBy(point => atan2(point.y - min.y, point.x - min.x))

    def newBorder(newPoint: Point, border: Points): Points = border match {
        case list if list.size < 3 => newPoint :: list
        case c :: b :: tail =>
          if(PointsUtil.det(c, newPoint, b) <= 0) {
            newBorder(newPoint, b::tail)
          } else {
            newPoint :: border
          }
      }

    sorted.foldLeft(List[Point]()) { case (acc, next) =>
        newBorder(next, acc)
    }
  }
}
