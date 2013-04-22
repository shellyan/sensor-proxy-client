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
import scala.collection.mutable.ListBuffer

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
    val selectionLog = new ListBuffer[String]()
  selectionLog += URL_ROOT
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.item_list)

    val loadItem = new loadItems(this).execute()
itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      override def onItemClick(parent: AdapterView[_], view: View, pos: Int, id: Long) {
        //if (itemList.length != 1 )
        selectionLog += (selectionLog.last + itemList(pos) + "/")
        Toast.makeText(view.getContext(), selectionLog.last, Toast.LENGTH_SHORT).show()
        val loadItem = new loadItems(LoadItemActivity.this).execute(selectionLog.last)

        def isAllDigits(x: String) = x forall Character.isDigit

      }
    })
  }

    override def onKeyDown(keyCode: Int, event: android.view.KeyEvent): Boolean = {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
      if (selectionLog.length == 1) {
        return super.onKeyDown(keyCode, event)
      }
      Toast.makeText(this.getApplicationContext(), selectionLog.init.last, Toast.LENGTH_SHORT).show()
      val loadItem = new loadItems(LoadItemActivity.this).execute(selectionLog.init.last)
      selectionLog.remove(selectionLog.length - 1)

      return true
    }

    return super.onKeyDown(keyCode, event)
  }
  
  class loadItems(activity: Activity) extends AsyncTask[AnyRef, Void, Boolean] {
    override protected def onPreExecute() {
      super.onPreExecute
      pDialog = new ProgressDialog(activity)
      pDialog.setMessage("Loading ...")
      pDialog.setIndeterminate(false)
      pDialog.setCancelable(false)
      pDialog.show()

    }

    override protected def doInBackground(params: AnyRef*): Boolean = {
      json = jsonParser.makeHttpRequest(URL_ROOT, "GET", params)
      if (params.length != 0) {
        json = jsonParser.makeHttpRequest(params(0).asInstanceOf[String], "GET", params)
      }
      val jsonParsed = parseFull(json)
      itemList = jsonParsed.get.asInstanceOf[List[Any]].map(_.toString())
      //      val jsonAst = json.asJson // or JsonParser(source)
      //      itemList = jsonAst.convertTo[List[String]]
      true

    }

    override protected def onPostExecute(result: Boolean) {
      pDialog.dismiss()
      textView.setText(json)
      val adapter = new ArrayAdapter[String](LoadItemActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList)
      itemListView.setAdapter(adapter)
    }

  }
  
  
}