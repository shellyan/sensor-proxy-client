package cs.luc.edu.client

import _root_.android.app.Activity
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
import android.view.View
import android.widget.Toast
import android.view.KeyEvent
class MainActivity extends Activity with TypedActivity {

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
  //  case class NamedList[A](items: List[A])
  //
  //  object MyJsonProtocol extends DefaultJsonProtocol {
  //    implicit def namedListFormat[A: JsonFormat] = jsonFormat1(NamedList.apply[A])
  //  }

  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.location)
    cd = new ConnectionDetector(getApplicationContext())
    if (!cd.isConnectingToInternet)
      alert.showAlertDialog(MainActivity.this, "Internet Connection Error",
        "Please connect to working Internet connection", false)

    val loadItem = new loadItems(this).execute()

    itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      override def onItemClick(parent: AdapterView[_], view: View, pos: Int, id: Long) {
        //        Toast.makeText(view.getContext(), itemList(pos), Toast.LENGTH_SHORT).show()
    	if(itemList.length!=1)  
        selectionLog += (selectionLog.last + itemList(pos) + "/")
        Toast.makeText(view.getContext(), selectionLog.last, Toast.LENGTH_SHORT).show()

        val loadItem = new loadItems(MainActivity.this).execute(selectionLog.last)

      }

    })
    //    getListView().setTextFilterEnabled(true)
    //    findView(TR.test).setText(itemList.toString())
  }

  override def onKeyDown(keyCode: Int, event: android.view.KeyEvent): Boolean = {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
       if (selectionLog.length == 1){
        return super.onKeyDown(keyCode, event)}
      Toast.makeText(this.getApplicationContext(), selectionLog.init.last, Toast.LENGTH_SHORT).show()
      val loadItem = new loadItems(MainActivity.this).execute(selectionLog.init.last)
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
      //      val params: List[NameValuePair] = null
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

      test.setText(json)
      val adapter = new ArrayAdapter[String](MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList)
      //val adapter = new ArrayAdapter[String](MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, ArrayBuffer("location1", "location2"))

      itemListView.setAdapter(adapter)
    }

  }
}
