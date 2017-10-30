package otto.com.ottorrino

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.os.Build
import android.app.Activity


/**
 * Created by vinic on 10/23/2017.
 */
class ControleBluetooth {

    companion object {

        fun hasPermissions(context: Context?, permissions: Array<String>): Boolean {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (permission in permissions) {
                    if (ActivityCompat.checkSelfPermission(context!!, permission) != PackageManager.PERMISSION_GRANTED) {
                        return false
                    }
                }
            }
            return true
        }

        fun grantPermissions(context: Context?) {
            val PERMISSION_ALL = 1
            val PERMISSIONS = arrayOf<String>(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH_PRIVILEGED,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION)

            if (!hasPermissions(context, PERMISSIONS)) {
                ActivityCompat.requestPermissions(context as Activity, PERMISSIONS, PERMISSION_ALL)
            }
        }
    }
}