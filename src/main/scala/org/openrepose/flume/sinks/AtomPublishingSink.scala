package org.openrepose.flume.sinks

import org.apache.flume.Context
import org.apache.flume.Sink.Status
import org.apache.flume.conf.Configurable
import org.apache.flume.sink.AbstractSink

class AtomPublishingSink extends AbstractSink with Configurable {
  override def configure(context: Context): Unit = ???

  override def process(): Status = ???
}
