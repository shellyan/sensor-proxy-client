package cs.luc.edu.client.helper

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import cs.luc.edu.client.R
 
class AlertDialogManager {
  /**
   * Function to display simple Alert Dialog
   * @param context - application context
   * @param title - alert dialog title
   * @param message - alert message
   * @param status - success/failure (used to set icon)
   *               - pass null if you don't want icon
   */
  def showAlertDialog(context: Context, title: String, message: String,
    status: Boolean) {
    val alertDialog = new AlertDialog.Builder(context).create()

    // Setting Dialog Title
    alertDialog.setTitle(title)

    // Setting Dialog Message
    alertDialog.setMessage(message)

    if (status != null)
      // Setting alert dialog icon
      alertDialog.setIcon(if(status)  R.drawable.success else R.drawable.fail)

    // Setting OK Button
    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
      def onClick(dialog: DialogInterface, which: Int) {
      }
    });

    // Showing Alert Message
    alertDialog.show()
  }
}