package pl.pej.asd.geometric

import math.atan2

object Graham {

  private type Points = List[Point]

  def scan(points: Points): Points = {

    val min: Point = points.minBy(_.y)

    val sorted: Points = points.sortBy(point => atan2(point.y - min.y, point.x - min.x))//.foldLeft(List.empty[Point])(addToHull)

    def newBorder(newPoint: Point, border: Points): Points = {

      border match {
        case Nil => newPoint::Nil

        case a :: Nil => newPoint :: a :: Nil

        case b :: a :: Nil => newPoint :: b :: a :: Nil

        case c :: b :: tail =>
          if(PointsUtil.det(c, newPoint, b) <= 0) {
            println(s"leaving out $c")
            newBorder(newPoint, b::tail)
          } else {
            newPoint :: border
          }
      }
    }

    def iterate(left: Points, acc: Points): Points = left match {

      case next :: tail =>

        val border = newBorder(next, acc)
        println(border)

        iterate(tail, border)
      case Nil => acc
    }


    iterate(sorted, Nil)
  }
}
