package me.lachlanap.dropunit.world

import scala.collection.{Map, Set}

class BlueprintSet(_blueprints: Set[Blueprint]) {
  private val blueprints: Map[String, Blueprint] = _blueprints.map(a => (a.id, a)).toMap

  def byName(name: String) = blueprints(name)
}

trait BlueprintLoader {
  def load(name: String): Blueprint

  def loadAll(): BlueprintSet
}