package com.aayushf.watchdog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import kotlinx.android.synthetic.main.content_scrolling.*
import java.util.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val i = Intent(this, WatchDogBackground::class.java)
        startService(i)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val db = FirebaseDatabase.getInstance()
        val logs_ref = db.getReference("logs").child("log_images")
        val logs_ref_cloud = FirebaseStorage.getInstance().getReference().child("logs")
        val logitems = mutableListOf<LogItem>()
        val myRef = db.getReference("auto")
        myRef.setValue("1")
        val live_feed_ref = db.getReference("live_feed")
        live_feed_ref.setValue("0")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                logitems.clear()
                dataSnapshot.children.forEach {
                    val fname = it.key!! + ".jpeg"
                    logs_ref_cloud.child(fname).downloadUrl.addOnSuccessListener { uri: Uri ->
                        logitems.add(LogItem(uri.toString(), fname))
                        val itemAdapter = ItemAdapter<LogItem>()
                        val fastAdapter = FastAdapter.with(itemAdapter)
                        home_rv.layoutManager = LinearLayoutManager(this@HomeActivity, RecyclerView.VERTICAL, false)
                        home_rv.adapter = fastAdapter
                        itemAdapter.add(logitems.reversed())
                        home_rv.adapter = fastAdapter
                    }.addOnFailureListener {
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        logs_ref.addValueEventListener(postListener)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Nothings gonna happen nigga ", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_drive -> {
                val i = Intent(this@HomeActivity, DriveActivity::class.java)
                startActivity(i)
            }
            R.id.nav_live_feed -> {
                val i = Intent(this@HomeActivity, LiveFeedActivity::class.java)
                startActivity(i)
            }

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    class LogItem(val url: String = "URL_NOT_GIVEN", val name: String = "NAME_NOT_GIVEN") :
        AbstractItem<LogItem.ViewHolder>() {
        init {
            Log.d("HomeActivity", "LogItem Made")
        }

        override val layoutRes: Int
            get() = R.layout.home_rv_card
        override val type: Int
            get() = 0

        override fun getViewHolder(v: View): ViewHolder {
            return ViewHolder(v)
        }

        class ViewHolder(val v: View) : FastAdapter.ViewHolder<LogItem>(v) {
            override fun bindView(item: LogItem, payloads: MutableList<Any>) {
                val iv = v.findViewById<ImageView>(R.id.rv_item_image)
                Glide.with(iv).load(item.url).into(iv)
                val d = Date(item.name.substringBefore('.').toLong())
                Log.d("Date", d.toString())
                val s = d.toString().substringBefore(" GMT")
                v.findViewById<TextView>(R.id.rv_item_text).text = s

            }

            override fun unbindView(item: LogItem) {

            }

        }
    }
}
