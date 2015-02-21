package me.lachlanap.dropunit.world

import me.lachlanap.dropunit.util.TraversableUtil._

case class Area(orientation: Orientation, columns: Array[Column], orders: List[BuildingOrder]) {
  def step(dt: Double, columnWidth: Double, fromCentre: Double): (Area, List[Transform]) = {
    def x(index: Int) = (if (orientation == FacingLeft) 1 else -1) * (fromCentre + columnWidth * index)

    val update = columns.map(doIndexed { (i, c) => c.step(dt, x(i), columnWidth) })
    val actions = update.flatMap(_._2).toList
    (new Area(orientation, update.map(_._1), orders), actions)
  }
}


case class BuildingOrder(column: Column, blueprint: Blueprint)

case class Column(stack: List[Block]) {
  def step(dt: Double, x: Double, blockHeight: Double): (Column, List[Transform]) = {
    val update = stack.map(doIndexed((i, b) => b.step(dt, Vector2(x, blockHeight * i))))
    val actions = update.flatMap(_._2)
    (new Column(update.map(_._1)), actions)
  }
}


sealed trait Orientation

case object FacingLeft extends Orientation

case object FacingRight extends Orientation