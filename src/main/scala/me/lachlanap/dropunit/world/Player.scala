package me.lachlanap.dropunit.world

import scala.collection.Set

case class Area(orientation: Orientation, columns: Array[Column], orders: Set[BuildingOrder])


case class BuildingOrder(column: Column, blueprint: Blueprint)

case class Column(stack: List[Block])

sealed trait Orientation

case object FacingLeft extends Orientation

case object FacingRight extends Orientation