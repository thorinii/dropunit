package me.lachlanap.dropunit.world

/**
 * An action to perform on the world, an area, a block, or an entity.
 */
trait Transform {
  def applyTo(world: World): World = world
}


case class SpawnEntity(entity: (Id) => Entity) extends Transform {
  override def applyTo(world: World): World = {
    new World(world.config, world.blueprints,
              world.leftPlayer, world.rightPlayer,
              entity(world.entityIdGenerator.next()) :: world.entities)
  }
}

case class DestroyEntity(entity: Entity) extends Transform {
  override def applyTo(world: World): World = {
    new World(world.config, world.blueprints,
              world.leftPlayer, world.rightPlayer,
              world.entities.filterNot(_.id == entity.id))
  }
}

case class DamageBlock(block: Block, amount: Double) extends Transform {
  override def applyTo(world: World): World = {
    new World(world.config, world.blueprints,
              world.leftPlayer.damage(block, amount), world.rightPlayer.damage(block, amount),
              world.entities)
  }
}