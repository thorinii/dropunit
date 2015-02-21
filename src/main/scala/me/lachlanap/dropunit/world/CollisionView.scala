package me.lachlanap.dropunit.world

/**
 * A view of the world suited for calculating collisions.
 */
class CollisionView(val world: World) {
  def intersectingBlock(at: Vector2): Option[(Area, Column, Block)] = {
    val area = if (at.x < 0) world.leftPlayer else world.rightPlayer
    val columnHeight = (at.x.abs - world.config.separation / 2) / world.config.columnWidth

    if (columnHeight < 0 || columnHeight >= world.config.columns)
      None
    else if (at.y < 0) {
      None
    } else {
      val columnIndex = columnHeight.toInt
      val column = area.columns(columnIndex)

      val blockIndex = (at.y / world.config.columnWidth).toInt

      if (blockIndex >= column.stack.length)
        None
      else
        Some((area, column, column.stack(blockIndex)))
    }
  }
}
