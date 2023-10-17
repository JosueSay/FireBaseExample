package edu.uvg.com.example.examplefbcars


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etCarMarca: EditText
    private lateinit var etCarModelo: EditText
    private lateinit var etCarAge: EditText
    private lateinit var etCarColor: EditText
    private lateinit var etCarCombustible: EditText
    private lateinit var etCarPrecio: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        // Datos para Car
        etCarMarca = findViewById(R.id.etMarcaCar)
        etCarModelo = findViewById(R.id.etModeloCar)
        etCarAge = findViewById(R.id.etAgeCar)
        etCarColor = findViewById(R.id.etColorCar)
        etCarCombustible = findViewById(R.id.etCombustibleCar)
        etCarPrecio = findViewById(R.id.etPrecioCar)

        // Botón de guardardo
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Carros")

        btnSaveData.setOnClickListener {
            saveCarData()
        }
    }

    private fun saveCarData() {

        //getting values
        val carMarca = etCarMarca.text.toString()
        val carModel = etCarModelo.text.toString()
        val carAge = etCarAge.text.toString()
        val carColor = etCarColor.text.toString()
        val carCombustible = etCarCombustible.text.toString()
        val carPrecio = etCarPrecio.text.toString()


        if (carMarca.isEmpty()) {
            etCarMarca.error = "Please enter name"
        }
        if (carModel.isEmpty()) {
            etCarModelo.error = "Please enter age"
        }
        if (carAge.isEmpty()) {
            etCarAge.error = "Please enter salary"
        }
        if (carColor.isEmpty()) {
            etCarColor.error = "Please enter name"
        }
        if (carCombustible.isEmpty()) {
            etCarCombustible.error = "Please enter age"
        }
        if (carPrecio.isEmpty()) {
            etCarPrecio.error = "Please enter salary"
        }

        val carId = dbRef.push().key!!
        val carro = CarModel(carId, carMarca, carModel, carAge.toInt(), carColor, carCombustible, carPrecio.toDouble())

        dbRef.child(carId).setValue(carro)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etCarMarca.text.clear()
                etCarModelo.text.clear()
                etCarAge.text.clear()
                etCarColor.text.clear()
                etCarCombustible.text.clear()
                etCarPrecio.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}
