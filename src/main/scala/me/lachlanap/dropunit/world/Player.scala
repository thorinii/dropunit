package me.lachlanap.dropunit.world

import scala.collection.Set

case class Area(orientation: Orientation, columns: Array[Column], orders: Set[BuildingOrder])


case class BuildingOrder(column: Column)
case class Column(stack: List[Unit])

sealed trait Orientation

case object FacingLeft extends Orientation
case object FacingRight extends Orientation