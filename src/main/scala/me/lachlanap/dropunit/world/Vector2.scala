package me.lachlanap.dropunit.world

object Vector2 {
  val Zero = Vector2(0, 0)

  def fromAngle(angle: Double): Vector2 = new Vector2(Math.cos(Math.toRadians(angle)),
                                                      Math.sin(Math.toRadians(angle)))
}

/**
 * A 2D vector.
 */
case class Vector2(x: Double, y: Double) {
  def +(o: Vector2) = new Vector2(x + o.x, y + o.y)

  def +(t: (Double, Double)) = new Vector2(x + t._1, y + t._2)

  def *(m: Double) = new Vector2(x * m, y * m)
}
