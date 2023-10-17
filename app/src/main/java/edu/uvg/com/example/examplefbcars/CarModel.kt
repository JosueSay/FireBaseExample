package edu.uvg.com.example.examplefbcars

data class CarModel(
    var carId: String? = null,
    var marca: String? = null,
    var modelo: String? = null,
    var año: Int? = null,
    var color: String? = null,
    var tipoDeCombustible: String? = null,
    var precio: Double? = null
)
