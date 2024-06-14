package cl.eduardozamora.eduardo_zamora_t1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import cl.eduardozamora.eduardo_zamora_t1.Modelo.PrecioProductos
import cl.eduardozamora.eduardo_zamora_t1.Modelo.cuenta


class MainActivity : AppCompatActivity() {

    private lateinit var etTotalPedido: EditText
    private lateinit var etPropina: EditText
    private lateinit var etTotalVenta: EditText
    private lateinit var etCant1: EditText
    private lateinit var etCant2: EditText
    private lateinit var etValorPastelDeChoclo: EditText
    private lateinit var etValorCazuela: EditText
    private lateinit var switchPropina: Switch

    private var cuenta: cuenta = cuenta()
    private var calcularPropina: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switchPropina = findViewById(R.id.switchPropina)
        etCant1 = findViewById(R.id.etCant1)
        etCant2 = findViewById(R.id.etCan2)
        etValorPastelDeChoclo = findViewById(R.id.etVentaPastelDeChoclo)
        etValorCazuela = findViewById(R.id.etVentaCazuela)
        etTotalPedido = findViewById(R.id.etTotalPedido)
        etTotalVenta = findViewById(R.id.etTotalVenta)
        etPropina = findViewById(R.id.etPropina)

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                actualizarvalores()
            }



            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        }

        etCant1.addTextChangedListener(textWatcher)
        etCant2.addTextChangedListener(textWatcher)

        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            calcularPropina = isChecked
            actualizarvalores()
        }

    }

    private fun actualizarvalores() {
        val cantidadpastelDeChoclo = etCant1.text.toString().toFloatOrNull()?: 0.0f
        val cantidadCazuela = etCant2.text.toString().toFloatOrNull()?: 0.0f

        val valorPastelDeChoclo = cantidadpastelDeChoclo * PrecioProductos.pastelDeChoclo.precio
        val valorCazuela = cantidadCazuela * PrecioProductos.cazuela.precio

        etValorPastelDeChoclo.setText(valorPastelDeChoclo.toString())
        etValorCazuela.setText(valorCazuela.toString())

        cuenta = cuenta(mutableListOf(valorPastelDeChoclo, valorCazuela))
        val totalPedido = cuenta.calcularTotalSinBonificacion()
        etTotalPedido.setText(totalPedido.toString())

        val propina = if (calcularPropina) totalPedido * 0.1f else 0.0f
        etPropina.setText(propina.toString())

        val totalVenta = totalPedido + propina
        etTotalVenta.setText(totalVenta.toString())

    }
}



