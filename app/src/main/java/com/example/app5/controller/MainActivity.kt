package com.example.app5.controller


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.example.app5.ItemAdapter
import com.example.app5.R
import com.example.app5.api.Client
import com.example.app5.api.Service
import com.example.app5.model.FollowersResponse
import com.example.app5.model.Item
import com.example.app5.model.ItemResponse
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noConnection: TextView
    private lateinit var item: Item
    private lateinit var swipeContainer: SwipeRefreshLayout

    private val requestCode = 200


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val searchButton = findViewById<FloatingActionButton>(R.id.searchSettingsFloatingButton)
        //searchButton.setOnClickListener{
        //    val intent = Intent(this, SearchSettingsActivity::class.java)
        //    startActivityForResult(intent, requestCode)
        //}

        initViews()

        swipeContainer = findViewById(R.id.swipeContainer)

        swipeContainer.setOnRefreshListener {
            loadJSON()
            Toast.makeText(this@MainActivity, "Github Users Refreshed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.userView)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.smoothScrollToPosition(0)
        loadJSON()
        //loadFollowersJSON()
    }

    //private fun loadFollowersJSON(items: List<Item>): Int{

    //}

    private fun loadJSON() {
        noConnection = findViewById(R.id.noConnection)
        try {
            val Client = Client()
            val apiService = Client.getClient()!!.create(Service::class.java)
            val call: Call<ItemResponse> = apiService.getItems()
            //val followers: Call<FollowersResponse> = apiService.getFollowers("ProfAvery")
            call.enqueue(object : Callback<ItemResponse?> {
                override fun onResponse(
                    call: Call<ItemResponse?>?,
                    response: Response<ItemResponse?>
                ) {
                    val items: List<Item> = response.body()?.getItems()!!
                    items[0].getLogin()
                    //val followers: Int = loadFollowersJSON(items)
                    recyclerView.adapter = ItemAdapter(applicationContext, items)
                    recyclerView.smoothScrollToPosition(0)
                    swipeContainer.isRefreshing = false
                }

                override fun onFailure(call: Call<ItemResponse?>?, t: Throwable) {

                    Toast.makeText(this@MainActivity, "Error Fetching Data!", Toast.LENGTH_SHORT)
                        .show()
                    noConnection.visibility = View.VISIBLE

                }
            })
        } catch (e: java.lang.Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }



}
