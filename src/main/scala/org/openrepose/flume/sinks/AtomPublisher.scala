package org.openrepose.flume.sinks

import java.util.{Date, UUID}

import org.apache.abdera.Abdera

object AtomPublisher {

  private val abdera = new Abdera()

  def pack(content: String): String = {
    val now = new Date()

    val entry = abdera.newEntry
    entry.setId(UUID.randomUUID().toString)
    entry.setTitle("User Access Event")
    entry.addAuthor("Repose")
    entry.setUpdated(now)
    entry.setContent(content)

    entry.toString
  }
}