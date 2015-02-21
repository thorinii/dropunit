package me.lachlanap.dropunit.world

/**
 * A unique id for entities, blocks, etc.
 */
case class Id(id: Long) extends AnyVal {
  override def toString() = "@" + id
}

class IdGenerator {
  private var counter = 0L

  def next() = {
    counter += 1
    new Id(counter)
  }
}