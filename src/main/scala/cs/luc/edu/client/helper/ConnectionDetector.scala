package cs.luc.edu.client.helper
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo;
import android.util.Log
class ConnectionDetector(context: Context) {
  //  var _context: Context = null
  //  def ConnectionDetector(context: Context) { _context = context }
  def isConnectingToInternet: Boolean = {

    val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE).asInstanceOf[ConnectivityManager]
    if (connectivity != null) {
      val info = connectivity.getAllNetworkInfo();
      if (info != null) {
        var i: NetworkInfo = null
        for (i <- info)
          if (i.getState() == NetworkInfo.State.CONNECTED)
            return true

      }
    }
    false
  }

}