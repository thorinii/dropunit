package me.lachlanap.dropunit.world

/**
 * A physically simulated game object.
 */
class Entity(val kind: String, val pos: Vector2, val vel: Vector2 = Vector2.Zero) {

}

object Entities {
  val CannonBall = "cannon-ball"

  def cannonBall(start: Vector2, owner: Orientation) = {
    new Entity(CannonBall, start)
  }
}