package cs.luc.edu.client
import spray.json._
import java.net.URL
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import android.graphics.Color
import android.os.AsyncTask
import android.widget.{ TextView }
import org.apache.http.util.EntityUtils
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.InputStream
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpPost
import android.util.Log
import org.apache.http.client.ClientProtocolException
import java.io.UnsupportedEncodingException
import java.io.IOException
import android.util.Log
import scala.util.parsing.json.JSON._
import scala.xml.Null
import spray.json.DefaultJsonProtocol._
import org.xml.sax.InputSource
import javax.xml.parsers.ParserConfigurationException
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Document
import java.io.StringReader
import org.xml.sax.SAXException
/**
 * Background task for checking remotely whether a number is prime.
 * Expects at the given URL a suitable cloud service such as an instance of
 * https://github.com/webservices-cs-luc-edu/primenumbers-spray-scala
 *
 * Using AnyRef instead of Long as the type parameter to work around
 * a problem with Android not finding our implementation of doInBackground.
 */
class RemoteTask(textview: TextView)
  extends AsyncTask[AnyRef, Void, Boolean] {

  private var request: HttpGet = null
  private var result: String = ""
  var is: InputStream = null
  var json: String = ""
  override protected def onPreExecute() {
  }

  override protected def doInBackground(params: AnyRef*): Boolean = {
    require { params.length <= 3 }
    val url = params(0).asInstanceOf[URL]
    lazy val requestOperation = params(1).asInstanceOf[String]
    lazy val configuration = params(2).asInstanceOf[String]

    val httpClient = new DefaultHttpClient()
    //    if (requestOperation == "get") {
    val request = new HttpGet(url.toString())
    //    }
    val configurationArray = configuration.split(":")
    //        request.setHeader("Accept", "application/xhtml+xml")
    request.setHeader(configurationArray(0), configurationArray(1))
    val httpResponse: HttpResponse = httpClient.execute(request)
    val httpEntity: HttpEntity = httpResponse.getEntity()
    val status = httpResponse.getStatusLine.getStatusCode
    result = EntityUtils.toString(httpEntity)

    if (status == 200)
      true
    else if (status == 404)
      false
    else	
      throw new RuntimeException("unexpected server response. status: "+status)

  }

  override protected def onPostExecute(result: Boolean) {

    if (result) {
      textview.setText(this.result)
      //val jsonParsed = JsonParser(this.result).toString

      //      val jsonParsed = parseFull(this.result).get
      //      textview.setText(jsonParsed.toString)
    } else textview.setText("Failed")

  }

}
