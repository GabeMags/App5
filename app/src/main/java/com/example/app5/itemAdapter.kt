package com.example.app5

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.app5.api.Service
import com.example.app5.controller.DetailActivity
import com.example.app5.model.FollowersResponse
import com.example.app5.api.Client
import com.example.app5.model.Followers


import com.example.app5.model.Item
import com.example.app5.model.ItemResponse
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates


class ItemAdapter(
    applicationContext: Context,
    itemArrayList: List<Item>
) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private val items: List<Item> = itemArrayList
    private val context: Context = applicationContext
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_user, viewGroup, false)
        return ViewHolder(view)
    }



    private fun getFollowers(login: String): String{
        var size: Int = 0
        val Client = Client()
        val apiService2 = Client.getClient()!!.create(Service::class.java)
        val followers: Call<List<Item>> = apiService2.getFollowers(login)
        followers.enqueue(object : Callback<List<Item>?> {
            override fun onResponse(
                followers: Call<List<Item>?>?,
                response: Response<List<Item>?>
            ) {
                //val items: List<Item> = response.body()?.getItems()!!
                val followersJSON: Int? = response.body()?.size
                if(followersJSON != null) {
                    size += followersJSON
                }
            }

            override fun onFailure(followers: Call<List<Item>?>?, t: Throwable) {

                Toast.makeText(context, "Error Fetching Data!", Toast.LENGTH_SHORT)
                    .show()
                //noConnection.visibility = View.VISIBLE
            }
        })
        return size.toString()
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.title.text = items[i].getLogin()
        viewHolder.githublink1.text = items[i].getHtmlUrl()
        viewHolder.followersNumber.text = getFollowers(items[i].getLogin())
        Picasso.get()
            .load(items[i].getAvatarUrl())
            .placeholder(R.drawable.app_icon)
            .into(viewHolder.imageView)
    }




    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val githublink1: TextView = view.findViewById(R.id.githubLink1)
        val imageView: ImageView = view.findViewById(R.id.profilePicture) as ImageView
        val followersNumber: TextView = view.findViewById(R.id.followersNumber)

        init {

            //on item click
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    val clickedDataItem: Item =
                        items[pos] //import the models item from project
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("login", items[pos].getLogin())
                    intent.putExtra("html_url", items[pos].getHtmlUrl())
                    intent.putExtra("avatar_url", items[pos].getAvatarUrl())
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                    Toast.makeText(
                        view.context,
                        "You clicked " + clickedDataItem.getLogin(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}
