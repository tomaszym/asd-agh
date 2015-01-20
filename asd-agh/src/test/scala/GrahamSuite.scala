import org.scalatest.FunSuite
import pl.pej.asd.geometric.{Graham, Point, PointsUtil}

import scala.util.Random

class GrahamSuite extends FunSuite {


  test("Relative points position det") {
    val oneLine = PointsUtil.det(Point(5,-4), Point(5, 2), Point(5, 100))
    assert(oneLine === 0)

    val positive = PointsUtil.det(Point(2, 3), Point(5,2), Point(4, 5))
    assert(positive > 0)

    val negative = PointsUtil.det(Point(2, 3), Point(5,2), Point(4, -5))
    assert(negative < 0)
  }

  test("Scans correctly with one point inside") {

    val points = List(
      Point(3,3), // point inside
      Point(1,2),
      Point(5,1),
      Point(8,15)
    )

    val res = Graham.scan(points)

    assert(
      res.toSet === points.tail.toSet
    )
  }

  test("Scans correctly with two close points inside") {

    val points = List(
      Point(3,3), // point inside
      Point(3,3.1), // second point inside
      Point(1,2),
      Point(5,1),
      Point(8,15)
    )

    val res = Graham.scan(points)

    assert(
      res.toSet === points.tail.tail.toSet
    )
  }

  test("Scans correctly with a lot of points inside") {

    val min = 0
    val max = 50
    def randomPos = Random.nextInt(max-1)+1
    val count = 500

    val pointsInside = List.fill(count){
      Point(randomPos, randomPos)
    }

    val borders = List(
      Point(min,min),
      Point(min,max),
      Point(max,min),
      Point(max,max)
    )

    val res = Graham.scan(borders ++ pointsInside)

    assert(
      res.toSet === borders.toSet
    )
  }

}
