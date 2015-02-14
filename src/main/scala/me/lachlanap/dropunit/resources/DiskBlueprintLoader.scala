package me.lachlanap.dropunit.resources

import java.nio.file.{Files, Paths}
import scala.collection.JavaConversions._

import com.typesafe.config.ConfigFactory
import me.lachlanap.dropunit.world._

/**
 * Loads Blueprints from disk
 */
class DiskBlueprintLoader extends BlueprintLoader {
  val gamedata = Paths.get(".", "gamedata")
  val blueprintsDir = gamedata.resolve("blueprints")

  override def loadAll(): BlueprintSet = {
    val blueprintIds = Files.newDirectoryStream(blueprintsDir)
      .map(blueprintsDir.relativize(_).toString)
      .filter(_.endsWith(".blpt"))
      .map(file => file.substring(0, file.length - 5))
      .toList

    val blueprints = blueprintIds.map(load _)
    new BlueprintSet(blueprints.toSet)
  }

  override def load(name: String): Blueprint = {
    val file = blueprintsDir.resolve(name + ".blpt").toFile

    val config = ConfigFactory.parseFile(file)

    val strength = Strength(config.getInt("strength"))
    val power = config.getInt("power") match {
      case p if p > 0 => PowerGeneration(p)
      case p if p < 0 => PowerConsumption(p)
      case _ => PowerNone
    }
    val attackStrength = Strength(config.getInt("attack-strength"))

    val specs = Specs(strength, power, attackStrength)
    Blueprint(id = name, name = config.getString("name"), specs)
  }
}
