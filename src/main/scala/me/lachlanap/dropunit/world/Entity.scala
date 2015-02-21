package me.lachlanap.dropunit.world

/**
 * A physically simulated game object.
 */
class Entity(val kind: String,
             val pos: Vector2, val vel: Vector2 = Vector2.Zero) {
  def step(dt: Double, gravity: Double): (Entity, List[Transform]) = {
    val (pos, vel) = integrate(dt, gravity, this.pos, this.vel)

    val next = new Entity(kind, pos, vel)
    (next, Nil)
  }

  def integrate(dt: Double, gravity: Double, pos: Vector2, vel: Vector2) = {
    // TODO: better integration; drag

    (pos + vel * dt, vel + new Vector2(0, -gravity) * dt)
  }
}

object Entities {
  val CannonBall = "cannon-ball"
  val CannonBallSpeed = 10.0

  def cannonBall(start: Vector2, angle: Double, owner: Orientation) = {
    new Entity(CannonBall, start, Vector2.fromAngle(angle) * CannonBallSpeed)
  }
}