package Dianna_Monsalve.com.example.restaurante.Modelo

class Pedido {
    val platos: MutableList<Plato> = mutableListOf()

        fun agregarPlato(plato: Plato) {
            platos.add(plato)
        }

        fun calcularSubtotal(plato: Plato): Double {
            return plato.cantidad * plato.precio
        }

        fun calcularTotalComida(): Double {
            return platos.sumByDouble { calcularSubtotal(it) }
        }

        fun calcularPropina(porcentaje: Double = 0.1): Double {
            return calcularTotalComida() * porcentaje
        }

        fun calcularTotalFinal(conPropina: Boolean): Double {
            return if (conPropina) calcularTotalComida() + calcularPropina() else calcularTotalComida()
        }
}