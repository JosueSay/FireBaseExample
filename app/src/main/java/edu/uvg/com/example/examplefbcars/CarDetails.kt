package edu.uvg.com.example.examplefbcars

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class CarDetails : AppCompatActivity() {
    private lateinit var tvCarId: TextView
    private lateinit var tvCarBrand: TextView
    private lateinit var tvCarModel: TextView
    private lateinit var tvCarYear: TextView
    private lateinit var tvCarColor: TextView
    private lateinit var tvFuelr: TextView
    private lateinit var tvCarPrice: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empMarca").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }

    }

    private fun initView() {
        tvCarId = findViewById(R.id.tvCarId)
        tvCarBrand = findViewById(R.id.tvCarBrand)
        tvCarModel = findViewById(R.id.tvCarModel)
        tvCarYear = findViewById(R.id.tvCarYear)
        tvCarColor = findViewById(R.id.tvCarColor)
        tvFuelr= findViewById(R.id.tvFuelr)
        tvCarPrice= findViewById(R.id.tvCarPrice)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvCarId.text = intent.getStringExtra("empId")
        tvCarBrand.text = intent.getStringExtra("empMarca")
        tvCarModel.text = intent.getStringExtra("empModelo")
        tvCarYear.text = intent.getStringExtra("empAnio")
        tvCarColor.text = intent.getStringExtra("empColor")
        tvFuelr.text = intent.getStringExtra("empTipoDeCombustible")
        tvCarPrice.text = intent.getStringExtra("empPrecio")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Carros").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Carros data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        empId: String,
        empName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etCarModel = mDialogView.findViewById<EditText>(R.id.etModeloCar)
        val etCarBrand = mDialogView.findViewById<EditText>(R.id.etMarcaCar)
        val etCarYear = mDialogView.findViewById<EditText>(R.id.etAgeCar)
        val etCarColor = mDialogView.findViewById<EditText>(R.id.etColorCar)
        val etCarCombus = mDialogView.findViewById<EditText>(R.id.etCombustibleCar)
        val etPrecio = mDialogView.findViewById<EditText>(R.id.etPrecioCar)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etCarBrand.setText(intent.getStringExtra("empMarca").toString())
        etCarModel.setText(intent.getStringExtra("empModelo").toString())
        etCarYear.setText(intent.getStringExtra("empAnio").toString())
        etCarColor.setText(intent.getStringExtra("empColor").toString())
        etCarCombus.setText(intent.getStringExtra("empTipoDeCombustible").toString())
        etPrecio.setText(intent.getStringExtra("empPrecio").toString())

        mDialog.setTitle("Updating $empName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                empId,
                etCarBrand.text.toString(),
                etCarModel.text.toString(),
                etCarYear.text.toString(),
                etCarColor.text.toString(),
                etCarCombus.text.toString(),
                etPrecio.text.toString()
            )

            Toast.makeText(applicationContext, "Car Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews

            tvCarBrand.text = etCarBrand.text.toString()
            tvCarModel.text = etCarModel.text.toString()
            tvCarYear.text = etCarYear.text.toString()
            tvCarColor.text = etCarColor.text.toString()
            tvFuelr.text = etCarCombus.text.toString()
            tvCarPrice.text = etPrecio.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        brand: String,
        model:String,
        age: String,
        color: String,
        combustible: String,
        precio:String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Carros").child(id)
        val agewhat = age.toInt()
        val preciot = precio.toDouble()
        val carrInfo = CarModel(id,brand,model,agewhat,color,combustible,preciot)
        dbRef.setValue(carrInfo)
    }
}