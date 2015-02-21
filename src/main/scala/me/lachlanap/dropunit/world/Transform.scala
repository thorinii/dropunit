package me.lachlanap.dropunit.world

/**
 * An action to perform on the world, an area, a block, or an entity.
 */
trait Transform {
  def applyTo(world: World): World = world
}


case class SpawnEntity(entity: Entity) extends Transform {
  override def applyTo(world: World): World = {
    // TODO: entity spawning
    new World(world.config, world.blueprints,
              world.leftPlayer, world.rightPlayer,
              entity :: world.entities)
  }
}