package me.lachlanap.dropunit.world.entities

import me.lachlanap.dropunit.world.{Entity, Orientation, Transform, Vector2}

object CannonBall {
  val Id = "cannon-ball"
  val CannonBallSpeed = 11.0

  def build(start: Vector2, angle: Double, owner: Orientation) = {
    new CannonBall(owner, start, Vector2.fromAngle(angle) * CannonBallSpeed)
  }
}

class CannonBall(owner: Orientation, pos: Vector2, vel: Vector2 = Vector2.Zero) extends Entity(CannonBall.Id, pos, vel) {


  def step(dt: Double, gravity: Double): (Entity, List[Transform]) = {
    val (pos, vel) = integrate(dt, gravity, this.pos, this.vel)

    val next = new CannonBall(owner, pos, vel)
    (next, Nil)
  }
}
