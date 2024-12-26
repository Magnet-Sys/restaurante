package Dianna_Monsalve.com.example.restaurante

import Dianna_Monsalve.com.example.restaurante.Modelo.Pedido
import Dianna_Monsalve.com.example.restaurante.Modelo.Plato
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.NumberFormat
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var pedido: Pedido
    private lateinit var etPasteles: EditText
    private lateinit var etCazuelas: EditText
    private lateinit var switchPropina: Switch
    private lateinit var tvSubtotalPasteles: TextView
    private lateinit var tvSubtotalCazuela: TextView
    private lateinit var tvTotalComida: TextView
    private lateinit var tvTotalPropina: TextView
    private lateinit var tvTotalFinal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

// Inicializar vistas
        etPasteles = findViewById(R.id.etPasteles)
        etCazuelas = findViewById(R.id.etCazuelas)
        switchPropina = findViewById(R.id.switchPropina)
        tvSubtotalPasteles = findViewById(R.id.tvSubtotalPasteles)
        tvSubtotalCazuela = findViewById(R.id.tvSubtotalCazuela)
        tvTotalComida = findViewById(R.id.tvTotalComida)
        tvTotalPropina = findViewById(R.id.tvTotalPropina)
        tvTotalFinal = findViewById(R.id.tvTotalFinal)

        // Inicializar el pedido y agregar los platos
        pedido = Pedido()
        val pastelDeChoclo = Plato("Pastel de Choclo", 12000.0, 0)
        val cazuela = Plato("Cazuela", 10000.0, 0)
        pedido.agregarPlato(pastelDeChoclo)
        pedido.agregarPlato(cazuela)

        // Configurar listeners para los EditText
        etPasteles.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                pastelDeChoclo.cantidad = s.toString().toIntOrNull() ?: 0
                actualizarMontos()
            }
        })

        etCazuelas.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                cazuela.cantidad = s.toString().toIntOrNull() ?: 0
                actualizarMontos()
            }
        })

        // Configurar listener para el Switch
        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            actualizarMontos(isChecked)
        }
    }

    // Función para actualizar los montos en la pantalla
    private fun actualizarMontos(conPropina: Boolean = switchPropina.isChecked) {
        val formatoMoneda = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

        tvSubtotalPasteles.text = formatoMoneda.format(pedido.calcularSubtotal(pedido.platos[0]))
        tvSubtotalCazuela.text = formatoMoneda.format(pedido.calcularSubtotal(pedido.platos[1]))
        tvTotalComida.text = formatoMoneda.format(pedido.calcularTotalComida())
        tvTotalPropina.text = formatoMoneda.format(pedido.calcularPropina())
        tvTotalFinal.text = formatoMoneda.format(pedido.calcularTotalFinal(conPropina))
    }
}