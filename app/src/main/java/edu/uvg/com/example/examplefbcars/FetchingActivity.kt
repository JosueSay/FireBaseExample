package edu.uvg.com.example.examplefbcars

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchingActivity : AppCompatActivity() {

    private lateinit var carRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var carList: ArrayList<CarModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        carRecyclerView = findViewById(R.id.rvCar)
        carRecyclerView.layoutManager = LinearLayoutManager(this)
        carRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        carList = arrayListOf<CarModel>()

        getCarsData()

    }

    private fun getCarsData() {

        carRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Carros")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                carList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(CarModel::class.java)
                        carList.add(empData!!)
                    }
                    val mAdapter = CarAdapter(carList)
                    carRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : CarAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, CarDetails::class.java)

                            //put extras
                            intent.putExtra("carMarca", carList[position].marca)
                            intent.putExtra("carModelo", carList[position].modelo)
                            intent.putExtra("carAge", carList[position].año)
                            intent.putExtra("carColor", carList[position].color)
                            intent.putExtra("carTipoDeCombustible", carList[position].tipoDeCombustible)
                            intent.putExtra("carPrecio", carList[position].precio)
                            startActivity(intent)
                        }

                    })

                    carRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}