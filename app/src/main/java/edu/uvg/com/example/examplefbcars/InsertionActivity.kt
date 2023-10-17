package edu.uvg.com.example.examplefbcars

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etEmpMarca: EditText
    private lateinit var etEmpModelo: EditText
    private lateinit var etEmpAnio: EditText
    private lateinit var etEmpColor: EditText
    private lateinit var etEmpTipoDeCombustible: EditText
    private lateinit var etEmpPrecio: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpMarca = findViewById(R.id.etEmpMarca)
        etEmpModelo = findViewById(R.id.etEmpModelo)
        etEmpAnio = findViewById(R.id.etEmpAnio)
        etEmpColor = findViewById(R.id.etEmpColor)
        etEmpTipoDeCombustible = findViewById(R.id.etEmpTipoDeCombustible)
        etEmpPrecio = findViewById(R.id.etEmpPrecio)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val empMarca = etEmpMarca.text.toString()
        val empModelo = etEmpModelo.text.toString()
        val empAnio = etEmpAnio.text.toString()
        val empColor = etEmpColor.text.toString()
        val empTipoDeCombustible = etEmpTipoDeCombustible.text.toString()
        val empPrecio = etEmpPrecio.text.toString()

        if (empMarca.isEmpty()) {
            etEmpMarca.error = "Por favor ingresa la Marca"
        }
        if (empModelo.isEmpty()) {
            etEmpModelo.error = "Por favor ingresa el Modelo"
        }
        if (empAnio.isEmpty()) {
            etEmpAnio.error = "Por favor ingresa el Salario"
        }
        if (empColor.isEmpty()) {
            etEmpColor.error = "Por favor ingresa el Color"
        }
        if (empTipoDeCombustible.isEmpty()) {
            etEmpTipoDeCombustible.error = "Por favor ingresa el Tipo de Combustible"
        }
        if (empPrecio.isEmpty()) {
            etEmpPrecio.error = "Por favor ingresa el Precio"
        }

        val empId = dbRef.push().key!!

        val employee = EmployeeModel(empMarca, empModelo, empAnio, empColor, empTipoDeCombustible, empPrecio)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etEmpMarca.text.clear()
                etEmpModelo.text.clear()
                etEmpAnio.text.clear()
                etEmpColor.text.clear()
                etEmpTipoDeCombustible.text.clear()
                etEmpPrecio.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}