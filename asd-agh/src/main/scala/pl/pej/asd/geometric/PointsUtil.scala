package pl.pej.asd.geometric

object PointsUtil {

  def det(a: Point, b: Point, c: Point): Double = a.x*b.y + b.x*c.y + c.x*a.y - c.x*b.y - b.x*a.y - a.x*c.y
}
