package cs.luc.edu.client.helper

import org.apache.http.NameValuePair
import org.apache.http.HttpResponse
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import java.net.URL
import java.io.UnsupportedEncodingException
import org.apache.http.client.ClientProtocolException
import java.io.IOException
import scala.util.parsing.json.JSONObject
import java.io.InputStream
import org.apache.http.util.EntityUtils

class JSONParser {
  var jObj: JSONObject = null
  var json = ""

  def makeHttpRequest(url: String, method: String,
    params: AnyRef*): String = {

    try {
      //check for request method
      if (method == "GET") {
        val httpClient = new DefaultHttpClient()
        
        val request = new HttpGet(url.toString())
        request.setHeader("Accept", "application/json")

        val httpResponse = httpClient.execute(request)
        val httpEntity = httpResponse.getEntity()
        val status = httpResponse.getStatusLine.getStatusCode
        json = EntityUtils.toString(httpEntity)
      }

    } catch {
      case e: UnsupportedEncodingException => e.printStackTrace()
      case e: ClientProtocolException => e.printStackTrace()
      case e: IOException => e.printStackTrace()

    }
    json
  }

}