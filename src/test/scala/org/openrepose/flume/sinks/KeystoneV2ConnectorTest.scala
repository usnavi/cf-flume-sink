package org.openrepose.flume.sinks

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import org.eclipse.jetty.server.handler.AbstractHandler
import org.eclipse.jetty.server.{Request, Server, ServerConnector}
import org.scalatest.{BeforeAndAfterAllConfigMap, ConfigMap, FunSpec}

import scala.util.Success

class KeystoneV2ConnectorTest extends FunSpec with BeforeAndAfterAllConfigMap {
  val testServer = new Server(0)
  testServer.setHandler(new KeystoneV2Handler())

  var localPort = -1
  var keystoneV2Connector: KeystoneV2Connector = _

  override def beforeAll(configMap: ConfigMap) {
    testServer.start()
    localPort = testServer.getConnectors()(0).asInstanceOf[ServerConnector].getLocalPort
    keystoneV2Connector = new KeystoneV2Connector(s"http://localhost:$localPort")
  }

  describe("generateToken") {
    it("should send a valid payload and receive a valid token for the user provided") {
      val token = keystoneV2Connector.generateToken("usr", "pwd")

      assert(token.isInstanceOf[Success[_]])
      assert("tkn-id".equals(token.get))
    }
  }

  override def afterAll(configMap: ConfigMap) {
    testServer.stop()
  }

  class KeystoneV2Handler extends AbstractHandler {
    override def handle(target: String,
                        baseRequest: Request,
                        request: HttpServletRequest,
                        response: HttpServletResponse): Unit = {
      response.getOutputStream.print("{\"access\":{\"token\":{\"id\":\"tkn-id\"}}}")
      response.getOutputStream.flush()
      response.getOutputStream.close()
    }
  }

}
