package org.openrepose.flume.sinks

import java.net.InetAddress

import com.typesafe.scalalogging.LazyLogging
import org.apache.http.HttpHost
import org.apache.http.client.config.RequestConfig

import scala.collection.JavaConverters._

object HttpClientConfigurator extends LazyLogging {

  def getConfig(httpProperties: Map[String, String]): RequestConfig = {
    val requestConfigBuilder = RequestConfig.custom()

    httpProperties foreach {
      case ("authenticationEnabled", value) => requestConfigBuilder.setAuthenticationEnabled(value.toBoolean)
      case ("circularRedirectsAllowed", value) => requestConfigBuilder.setCircularRedirectsAllowed(value.toBoolean)
      case ("connectionRequestTimeout", value) => requestConfigBuilder.setConnectionRequestTimeout(value.toInt)
      case ("connectTimeout", value) => requestConfigBuilder.setConnectTimeout(value.toInt)
      case ("cookieSpec", value) => requestConfigBuilder.setCookieSpec(value)
      case ("expectContinueEnabled", value) => requestConfigBuilder.setExpectContinueEnabled(value.toBoolean)
      case ("localAddress", value) => requestConfigBuilder.setLocalAddress(InetAddress.getByName(value))
      case ("maxRedirects", value) => requestConfigBuilder.setMaxRedirects(value.toInt)
      case ("proxy", value) => requestConfigBuilder.setProxy(new HttpHost(value))
      case ("proxyPreferredAuthSchemes", value) => requestConfigBuilder.setProxyPreferredAuthSchemes(value.split(',').toSeq.asJavaCollection)
      case ("redirectsEnabled", value) => requestConfigBuilder.setRedirectsEnabled(value.toBoolean)
      case ("relativeRedirectsAllowed", value) => requestConfigBuilder.setRelativeRedirectsAllowed(value.toBoolean)
      case ("socketTimeout", value) => requestConfigBuilder.setSocketTimeout(value.toInt)
      case ("staleConnectionCheckEnabled", value) => requestConfigBuilder.setStaleConnectionCheckEnabled(value.toBoolean)
      case ("targetPreferredAuthSchemas", value) => requestConfigBuilder.setTargetPreferredAuthSchemes(value.split(',').toSeq.asJavaCollection)
      case (key, _) =>
        val errMsg = s"""Unrecognized HttpClient property: $key , unable to configure a HttpClient"""
        logger.error(errMsg)
        throw new Exception(errMsg)
    }

    requestConfigBuilder.build()
  }
}
