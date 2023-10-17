package edu.uvg.com.example.examplefbcars

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

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
        if (empAge.isEmpty()) {
            etCarModelo.error = "Please enter age"
        }
        if (empSalary.isEmpty()) {
            etCarAge.error = "Please enter salary"
        }
        if (empName.isEmpty()) {
            etCarColor.error = "Please enter name"
        }
        if (empAge.isEmpty()) {
            etCarCombustible.error = "Please enter age"
        }
        if (empSalary.isEmpty()) {
            etCarPrecio.error = "Please enter salary"
        }


        val carId = dbRef.push().key!!

        val employee = CarModel(empId, empName, empAge, empSalary)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etEmpName.text.clear()
                etEmpAge.text.clear()
                etEmpSalary.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}