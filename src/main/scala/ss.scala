// import android.os.AsyncTask
// import scala.collection.mutable.ArrayBuffer
// import org.apache.http.NameValuePair
// 
// class loadLocation extends AsyncTask[String, Void, Boolean] {
//
//    override protected def onPreExecute() {
//      super.onPreExecute();
//      pDialog = new ProgressDialog(MainActivity.this);
//      pDialog.setMessage("Listing locations ...");
//      pDialog.setIndeterminate(false);
//      pDialog.setCancelable(false);
//      pDialog.show();
//    }
//    override protected def doInBackground(args: String*) = {
//      val params = new ArrayBuffer[NameValuePair]
//      var json = jsonParser.makeHttpRequest(URL_LOCATION, "GET", params.toList)
//      val jsonObj = parseFull(json)
//      true
//    }
//
//    
//     /**
//         * After completing background task Dismiss the progress dialog
//         * **/
//    override protected def onPostExecute(result: Boolean)  {
//
//      pDialog.dismiss()
//      runOnUiThread(new Runnable() {
//        def run() {
// /**
//                     * Updating parsed JSON data into ListView
//                     * */
//          val adapter = new SimpleAdapter(LocationActivity.this,jsonObj,R.layout.location,new String("Device"),new int(R.id.album_id))
//        
//        
//        }
//      })
//      
//      
//    }
//
//  }