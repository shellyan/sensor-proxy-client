package cs.luc.edu.client

import _root_.android.app.Activity
import spray.json._
import DefaultJsonProtocol._
import _root_.android.os.Bundle
import cs.luc.edu.client.helper._
import android.app.ProgressDialog

import android.os.AsyncTask
import org.apache.http.NameValuePair
import android.widget.SimpleAdapter
import scala.util.parsing.json.JSON._

import scala.collection.JavaConversions._

import android.widget.ArrayAdapter
import scala.util.parsing.json._
import android.widget.AdapterView
import android.view._
import android.widget._
import android.content.Intent

class LoadItemActivity extends Activity with TypedActivity {
  var pDialog: ProgressDialog = null
  var json = ""
  var itemList: List[String] = null
  //  var listView: ListView = null
  val URL_ROOT = "http://pervasive-dev.cs.luc.edu:8080/sensor-proxy-restful/"
  val jsonParser = new JSONParser()
  lazy val itemListView = findView(TR.list)
  lazy val textView = findView(TR.textView)
  val list = List("hello")
  
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.item_list)
    textView.setText("HHHOOOLLLAAAA")
        val adapter = new ArrayAdapter[String](LoadItemActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, List("hi","it is working"))
    //
        itemListView.setAdapter(adapter)

  }

}