package com.example.app5.controller


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.app5.ItemAdapter
import com.example.app5.R
import com.example.app5.api.Client
import com.example.app5.api.Service
import com.example.app5.model.Item
import com.example.app5.model.ItemResponse
import kotlinx.android.synthetic.main.activity_main.view.*
import retrofit2.*


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noConnection: TextView
    private lateinit var item: Item
    //var pd: ProgressDialog? = null
    private lateinit var swipeContainer: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        swipeContainer = findViewById(R.id.swipeContainer)

        //swipeContainer.setColorSchemeResources(R.color.holo_orange_dark)
        swipeContainer.setOnRefreshListener {
            loadJSON()
            Toast.makeText(this@MainActivity, "Github Users Refreshed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViews() {
        //pd = ProgressDialog(this)
        //pd.setMessage("Fetching Github Users...")
        //pd.setCancelable(false)
        //pd.show()
        recyclerView = findViewById(R.id.userView)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.smoothScrollToPosition(0)
        loadJSON()
    }

    private fun loadJSON() {
        noConnection = findViewById(R.id.noConnection)
        try {
            val Client = Client()
            val apiService = Client.getClient()!!.create(Service::class.java)
            val call: Call<ItemResponse> = apiService.getItems()
            call.enqueue(object : Callback<ItemResponse?> {
                override fun onResponse(
                    call: Call<ItemResponse?>?,
                    response: Response<ItemResponse?>
                ) {
                    val items: List<Item> = response.body()?.getItems()!!
                    recyclerView.adapter = ItemAdapter(applicationContext, items)
                    recyclerView.smoothScrollToPosition(0)
                    swipeContainer.isRefreshing = false
                    //pd.hide()
                }

                override fun onFailure(call: Call<ItemResponse?>?, t: Throwable) {
                    //Log.d("Error", t.message)
                    Toast.makeText(this@MainActivity, "Error Fetching Data!", Toast.LENGTH_SHORT)
                        .show()
                    noConnection.visibility = View.VISIBLE
                    //pd.hide()
                }
            })
        } catch (e: java.lang.Exception) {
            //Log.d("Error", e.message)
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}
