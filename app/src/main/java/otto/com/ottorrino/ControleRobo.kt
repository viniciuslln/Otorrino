package otto.com.ottorrino

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import kotlin.concurrent.thread

/**
 * Created by vinic on 10/25/2017.
 */
class ControleRobo{

    companion object {

        internal var btSocket: BluetoothSocket? = null
        internal var btServerSocket: BluetoothServerSocket? = null
        internal var input: InputStream? = null
        internal var output: OutputStream? = null
        internal var btDevAddress: String? = null
        internal var myUUID = "00001101-0000-1000-8000-00805F9B34FB"
        internal var server: Boolean = false
        internal var running = false
        internal var isConnected = false

        public enum class Movimento{
            FRENTE,
            TRAS,
            ESQUERDA,
            DIREITA,
            PARAR
        }

        fun conectar(endereco: String){
            btDevAddress = endereco
            val btAdapter = BluetoothAdapter.getDefaultAdapter()
            val btDevice = btAdapter.getRemoteDevice(btDevAddress)

            btSocket = btDevice.createRfcommSocketToServiceRecord(UUID.fromString(myUUID))
            btSocket?.connect()

            output = btSocket?.outputStream

            /*for (i in 1..10){
                output?.write(i)
                Thread.sleep(1000)
            }*/
        }

        fun enviar(mov: Movimento){
            when(mov){
                ControleRobo.Companion.Movimento.FRENTE -> output?.write(49)
                ControleRobo.Companion.Movimento.TRAS -> output?.write(50)
                ControleRobo.Companion.Movimento.DIREITA -> output?.write(51)
                ControleRobo.Companion.Movimento.ESQUERDA -> output?.write(52)
                ControleRobo.Companion.Movimento.PARAR -> output?.write(53)
            }
        }
    }
}