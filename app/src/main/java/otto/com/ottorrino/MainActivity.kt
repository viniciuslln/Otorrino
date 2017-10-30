package otto.com.ottorrino

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.bluetooth.BluetoothDevice
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter
import otto.com.ottorrino.R.mipmap.ic_launcher
import android.bluetooth.BluetoothAdapter
import android.widget.Button


class MainActivity : AppCompatActivity() {

    private var mTextMessage: TextView? = null
    private var mImageButtonMover: ImageButton? = null
    private var mImageButtonStop: ImageButton? = null
    private var mImageButtonEsquerda: ImageButton? = null
    private var mImageButtonDireita: ImageButton? = null
    private var mImageButtonVoltar: ImageButton? = null
    private var mImageButtonFrente: ImageButton? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mTextMessage!!.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                mTextMessage!!.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                mTextMessage!!.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextMessage = findViewById(R.id.message) as TextView
        //val navigation = findViewById(R.id.navigation) as BottomNavigationView
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mImageButtonMover = findViewById(R.id.imageButtonMover) as ImageButton
        mImageButtonDireita = findViewById(R.id.imageButtonDireita) as ImageButton
        mImageButtonEsquerda = findViewById(R.id.imageButtonEsquerda) as ImageButton
        mImageButtonFrente = findViewById(R.id.imageButtonFrente) as ImageButton
        mImageButtonVoltar = findViewById(R.id.imageButtonVoltar) as ImageButton
        mImageButtonStop = findViewById(R.id.imageButtonStop) as ImageButton

        mImageButtonMover!!.setOnClickListener( moverClick )
        mImageButtonEsquerda!!.setOnClickListener( esquerdaClick )
        mImageButtonDireita!!.setOnClickListener( direitaClick )
        mImageButtonFrente!!.setOnClickListener( frenteClick )
        mImageButtonVoltar!!.setOnClickListener( voltarClick )
        mImageButtonStop!!.setOnClickListener( stopClick )

        ControleBluetooth.grantPermissions(this)

        (findViewById(R.id.button_conectar) as Button).setOnClickListener({onButtonPairedDevicesClick()})
    }

    var moverClick =  View.OnClickListener {}
    var esquerdaClick =  View.OnClickListener {ControleRobo.enviar(ControleRobo.Companion.Movimento.ESQUERDA)}
    var direitaClick =  View.OnClickListener {ControleRobo.enviar(ControleRobo.Companion.Movimento.DIREITA)}
    var frenteClick =  View.OnClickListener {ControleRobo.enviar(ControleRobo.Companion.Movimento.FRENTE)}
    var voltarClick =  View.OnClickListener {ControleRobo.enviar(ControleRobo.Companion.Movimento.TRAS)}
    var stopClick =  View.OnClickListener {ControleRobo.enviar(ControleRobo.Companion.Movimento.PARAR)}

    fun onButtonPairedDevicesClick() {

        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val pairedDevices = mBluetoothAdapter.bondedDevices

        var listDevices = ArrayList<BluetoothDevice>()
        for (bt in pairedDevices) {
            listDevices.add(bt)
        }

        println("<><>Paired<><> " + listDevices.size)
        mostrarDispositivos(listDevices)
    }

    fun mostrarDispositivos(listDevices: ArrayList<BluetoothDevice>){
        val builderSingle = AlertDialog.Builder(this)
        builderSingle.setTitle("Select One Name:-")

        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice)
        for(bt in listDevices){
            arrayAdapter.add(bt.address)
        }

        var escolhido : Number

        builderSingle.setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, which ->
            run {
                escolhido = which
                dialog.dismiss()
            }
        } )

        builderSingle.setAdapter(arrayAdapter, DialogInterface.OnClickListener { dialog, which ->
            val strName = arrayAdapter.getItem(which)
            val builderInner = AlertDialog.Builder(this)
            builderInner.setMessage(strName)
            builderInner.setTitle("Your Selected Item is")
            builderInner.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                ControleRobo.conectar(strName)
            })
            builderInner.show()

        })
        builderSingle.show()

    }

}
