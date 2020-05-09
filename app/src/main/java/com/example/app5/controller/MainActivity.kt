package com.example.app5.controller

//import android.R
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.app5.R
import com.example.app5.api.Client
import com.example.app5.api.Service
import com.example.app5.model.Item
import com.example.app5.model.ItemResponse
import retrofit2.Call
import retrofit2.Callback


class MainActivity : AppCompatActivity() {

    private var recyclerview: RecyclerView? = null
    private var noConnection: TextView? = null
    private val item: Item? = null
    //var pd: ProgressDialog? = null
    private var swipeContainer: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        swipeContainer = findViewById(R.id.swipeContainer)

        //swipeContainer.setColorSchemeResources(R.color.holo_orange_dark)
        swipeContainer.setOnRefreshListener(OnRefreshListener {
            loadJSON()
            Toast.makeText(this@MainActivity, "Github Users Refreshed", Toast.LENGTH_SHORT)
                .show()
        })
    }

    private fun initViews() {
        //pd = ProgressDialog(this)
        //pd.setMessage("Fetching Github Users...")
        //pd.setCancelable(false)
        //pd.show()
        recyclerview = findViewById(R.id.userView)
        recyclerview.setLayoutManager(LinearLayoutManager(applicationContext))
        recyclerview.smoothScrollToPosition(0)
        loadJSON()
    }

    private fun loadJSON() {
        noConnection = findViewById(R.id.noConnection)
        try {
            val client = Client()
            val apiService: Service = client.getClient().create(Service::class.java)
            val call: Call<ItemResponse?>? = apiService.getItems()
            //here
            call.enqueue(Callback<ItemResponse>(){

            })
        } catch (e: Exception) {
            Log.d("Error", e.message)
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}
