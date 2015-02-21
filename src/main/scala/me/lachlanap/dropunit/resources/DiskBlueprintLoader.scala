package me.lachlanap.dropunit.resources

import java.nio.file.{Files, Paths}

import com.typesafe.config.{Config, ConfigFactory}
import me.lachlanap.dropunit.world._
import me.lachlanap.dropunit.world.controllers.CannonController

import scala.collection.JavaConversions._

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

    val blueprints = blueprintIds.map(load)
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

    val controllerFactory = controllerFactoryFor(config.getOptionalString("controller"))

    val specs = Specs(strength, power, attackStrength)
    Blueprint(id = name, name = config.getString("name"), specs, controllerFactory)
  }

  private def controllerFactoryFor(id: Option[String]): () => BlockController = {
    id.map {
             case "cannon" => () => CannonController()
             case _ => () => NilController
           }.getOrElse(() => NilController)
  }

  implicit class RichConfig(val underlying: Config) {
    def getOptionalString(path: String): Option[String] = if (underlying.hasPath(path)) {
      Some(underlying.getString(path))
    } else {
      None
    }
  }
}
