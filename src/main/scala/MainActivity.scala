package cs.luc.edu.client

import _root_.android.app._
import spray.json._
import DefaultJsonProtocol._
import _root_.android.os.Bundle
import cs.luc.edu.client.helper._
import android.app.ProgressDialog
import scala.collection.mutable.ArrayBuffer
import scala.collection.Map
import android.os.AsyncTask
import org.apache.http.NameValuePair
import android.widget.SimpleAdapter
import scala.util.parsing.json.JSON._
import scala.collection.mutable.ListBuffer
import android.widget.ArrayAdapter
import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import scala.util.parsing.json._
import android.widget.AdapterView
import android.view._
import android.widget._
import android.content.Intent
class MainActivity extends TabActivity with TypedActivity {

  var cd: ConnectionDetector = null
  lazy val alert = new AlertDialogManager()
  var pDialog: ProgressDialog = null
  var itemList: List[String] = null
  val URL_ROOT = "http://pervasive-dev.cs.luc.edu:8080/sensor-proxy-restful/"
  val jsonParser = new JSONParser()
  val selectionLog = new ListBuffer[String]()
  selectionLog += URL_ROOT
  lazy val itemListView = findView(TR.location_list)
  lazy val test = findView(TR.test)
  var json = ""


  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.location)
    cd = new ConnectionDetector(getApplicationContext())
    if (!cd.isConnectingToInternet)
      alert.showAlertDialog(MainActivity.this, "Internet Connection Error",
        "Please connect to working Internet connection", false)
    var intent = new Intent(this,classOf[LoadItemActivity])

    //
    lazy val th = getTabHost()

    th.setup();
    var specs = th.newTabSpec("tag1")
    specs.setContent(R.id.tab1)
    specs.setIndicator("Tab1")
    th.addTab(specs)
    specs = th.newTabSpec("tag2")
    specs.setContent(R.id.tab2)
    specs.setIndicator("Tab2")
    th.addTab(specs)
    specs = th.newTabSpec("tag3")
//    specs.setContent(R.id.tab3)
    specs.setContent(intent)
    specs.setIndicator("Tab3")
    th.addTab(specs)

    

    //

  }



  
}
