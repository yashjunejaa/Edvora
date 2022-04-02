package com.example.myapplication

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {

    //var rs : List<Rides>?= null;

    var ridess= mutableListOf<Rides>()
    var ridesList:RecyclerView?=null
    var userCode:Int=0
    var cities = mutableMapOf<String, MutableList<String>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ridesList=findViewById<RecyclerView>(R.id.ridesList)
        getUser()
        getViews()

//        var sp1 = findViewById<Spinner>(R.id.state)
//        sp1!!.setonitem

        /*var ridesList = findViewById<RecyclerView>(R.id.ridesList)
        ridesList.adapter = RidesAdapter(r)
        ridesList.layoutManager = LinearLayoutManager(this)*/
    }


    fun getUser()
    {
        val user = RideService.rideInstance.getUser()
        user.enqueue(object:Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val u=response.body();
                if (u != null) {
                    userCode=u.station_code
                    var name = findViewById<TextView>(R.id.Name)
                    name.text = u.name
                    var img = findViewById<CircleImageView>(R.id.profile_image)
                    Picasso.with(this@MainActivity).load(u.url).into(img);
                    Log.d("Code",u.toString())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    fun getViews() {
        val rides = RideService.rideInstance.getRides()
        rides.enqueue(object: Callback<List<Rid>>{
            override fun onResponse(call: Call<List<Rid>>, response: Response<List<Rid>>) {
                val r = response.body()
                if (r != null) {
                    for(e in r) {

                        var dist=Int.MAX_VALUE
                        for(i in e.station_path)
                        {
                            if(dist>Math.abs(userCode-i))
                            {
                                dist=Math.abs(userCode-i)

                            }
                        }
                        if(!cities.containsKey(e.state))
                            cities.put(e.state, mutableListOf<String>())
                        cities.get(e.state)?.add(e.city)
                        ridess.add(Rides(e.id,e.city,e.state,e.origin_station_code,e.destination_station_code,e.station_path.toIntArray(),e.date,e.map_url,dist))
                    }

                    ridesList?.adapter = RidesAdapter(this@MainActivity,ridess)
                    ridesList?.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }

            override fun onFailure(call: Call<List<Rid>>, t: Throwable) {
                Log.d("ff","Failes")
            }
        })
    }

    fun getNearest(view: View) {
        var tv1 = findViewById<TextView>(R.id.upcoming);
        tv1.setTypeface(null, Typeface.NORMAL);
        var tv2 = findViewById<TextView>(R.id.past);
        tv1.setTypeface(null, Typeface.NORMAL);
        tv1.setTextColor(Color.parseColor("#ababab"));
        tv2.setTextColor(Color.parseColor("#ababab"));
        var l2 = findViewById<View>(R.id.line2)
        l2.visibility = View.INVISIBLE
        var l3 = findViewById<View>(R.id.line3)
        l3.visibility = View.INVISIBLE
        var tv = findViewById<TextView>(R.id.nearest);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextColor(Color.parseColor("#ffffff"));
        var l1 = findViewById<View>(R.id.line1)
        l1.visibility = View.VISIBLE
        ridess.sortWith(Comparator { s1, s2 ->
            when {
                s1.distance > s2.distance -> 1
                s1.distance < s2.distance ->-1
                else -> 0
            }
        })
        for (i in ridess) Log.d("rides",i.toString());
        ridesList?.adapter = RidesAdapter(this@MainActivity,ridess)
        ridesList?.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    fun getUpcoming(view: View) {
        var tv1 = findViewById<TextView>(R.id.nearest);
        tv1.setTypeface(null, Typeface.NORMAL);
        var tv2 = findViewById<TextView>(R.id.past);
        tv1.setTypeface(null, Typeface.NORMAL);
        tv1.setTextColor(Color.parseColor("#ababab"));
        tv2.setTextColor(Color.parseColor("#ababab"));
        var l3 = findViewById<View>(R.id.line3)
        l3.visibility = View.INVISIBLE
        var l1 = findViewById<View>(R.id.line1)
        l1.visibility = View.INVISIBLE
        var tv = findViewById<TextView>(R.id.upcoming);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextColor(Color.parseColor("#ffffff"));
        var l2 = findViewById<View>(R.id.line2)
        l2.visibility = View.VISIBLE
        var rrr = mutableListOf<Rides>()
        for (i in ridess) {
            if (Date(i.date).compareTo(Date())>0) rrr.add(i)
        }
        rrr.sortWith(Comparator { s1, s2 ->
            when {
                Date(s1.date).compareTo(Date(s2.date))>0 -> 1
                Date(s1.date).compareTo(Date(s2.date))<0 -> -1
                else -> 0
            }
        })
        ridesList?.adapter = RidesAdapter(this@MainActivity,rrr)
        ridesList?.layoutManager = LinearLayoutManager(this@MainActivity)
    }
    fun getPast(view: View) {
        var tv1 = findViewById<TextView>(R.id.upcoming);
        tv1.setTypeface(null, Typeface.NORMAL);
        var tv2 = findViewById<TextView>(R.id.nearest);
        tv1.setTypeface(null, Typeface.NORMAL);
        tv1.setTextColor(Color.parseColor("#ababab"));
        tv2.setTextColor(Color.parseColor("#ababab"));
        var l1 = findViewById<View>(R.id.line1)
        l1.visibility = View.INVISIBLE
        var l2 = findViewById<View>(R.id.line2)
        l2.visibility = View.INVISIBLE
        var tv = findViewById<TextView>(R.id.past);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextColor(Color.parseColor("#ffffff"));
        var l3 = findViewById<View>(R.id.line3)
        l3.visibility = View.VISIBLE
        var rrr = mutableListOf<Rides>()
        for (i in ridess) {
            if (Date(i.date).compareTo(Date())<0) rrr.add(i)
        }
        rrr.sortWith(Comparator { s1, s2 ->
            when {
                Date(s1.date).compareTo(Date(s2.date))<0 -> 1
                Date(s1.date).compareTo(Date(s2.date))>0 -> -1
                else -> 0
            }
        })
        ridesList?.adapter = RidesAdapter(this@MainActivity,rrr)
        ridesList?.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    fun getPopUp(view: View) {
        val popupMenu: PopupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menus,popupMenu.menu)
    }
}
