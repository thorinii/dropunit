package me.lachlanap.dropunit.world

import me.lachlanap.dropunit.util.TraversableUtil._

case class Area(orientation: Orientation, columns: Array[Column], orders: List[BuildingOrder]) {
  def step(dt: Double, columnWidth: Double, fromCentre: Double): (Area, List[Transform]) = {
    def x(index: Int) = (if (orientation == FacingLeft) 1 else -1) * (fromCentre + columnWidth * index + columnWidth / 2)

    val update = columns.map(doIndexed { (i, c) => c.step(dt, x(i), columnWidth, orientation) })
    val actions = update.flatMap(_._2).toList
    (new Area(orientation, update.map(_._1), orders), actions)
  }

  def damage(block: Block, amount: Double): Area = {
    copy(columns = columns.map(_.damage(block, amount)))
  }
}


case class BuildingOrder(column: Column, blueprint: Blueprint)

case class Column(stack: List[Block]) {
  def step(dt: Double, x: Double, blockHeight: Double, orientation: Orientation): (Column, List[Transform]) = {
    val update = stack.map(doIndexed((i, b) => b.step(dt, Vector2(x, blockHeight * i + blockHeight / 2), orientation)))
    val actions = update.flatMap(_._2)
    (new Column(update.map(_._1)), actions)
  }

  def damage(block: Block, amount: Double): Column = {
    // TODO: Remove the filter() instead calling a DestroyBlock transform
    Column(stack.map { b => if (b.id == block.id) b.damage(amount) else b } filter (_.health > 0))
  }
}


sealed trait Orientation {
  def inverse: Orientation
}

case object FacingLeft extends Orientation {
  val inverse = FacingRight
}


case object FacingRight extends Orientation {
  val inverse = FacingLeft
}