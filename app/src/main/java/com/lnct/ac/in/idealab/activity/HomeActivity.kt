package com.lnct.ac.`in`.idealab.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.lnct.ac.`in`.idealab.R
import com.lnct.ac.`in`.idealab.frgments.AboutUs
import com.lnct.ac.`in`.idealab.frgments.ContactUs
import com.lnct.ac.`in`.idealab.frgments.Event
import com.lnct.ac.`in`.idealab.frgments.HomeFragment

class HomeActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var navigationView: NavigationView
    lateinit var fm: FragmentManager
    lateinit var linkeden_link: ImageView
    lateinit var instagram_link: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.nav_drawer)
        navigationView = findViewById(R.id.navigation_view)
        navigationView.itemIconTintList = null
        toolbar = findViewById(R.id.tool_bar)
        linkeden_link = findViewById(R.id.linkedin_link)
        instagram_link = findViewById(R.id.instagram_link)

       setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open_Nav,R.string.Close_Nav)
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()
        loadFragment(HomeFragment(),"Home")

        navigationView.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val id = item.itemId

                when(id){
//                    R.id.home -> Toast.makeText(this@HomeActivity,"HOME",Toast.LENGTH_SHORT).show()
                    R.id.home -> loadFragment(HomeFragment(),"Home")
//                    R.id.event -> Toast.makeText(this@HomeActivity,"Event",Toast.LENGTH_SHORT).show()
                    R.id.event -> loadFragment(Event(),"Events")
                    R.id.about -> loadFragment(AboutUs(),"About Us")
                    R.id.contact -> loadFragment(ContactUs(),"Contact Us")
                    R.id.highlights -> Toast.makeText(this@HomeActivity,"Highlight",Toast.LENGTH_SHORT).show()
                    R.id.projects -> Toast.makeText(this@HomeActivity,"Projects",Toast.LENGTH_SHORT).show()
                    else -> {}

                }

      //  var transaction = getSupportFragmentManager().beginTransaction()
      //  transaction.replace(R.id.frame_layout, ContactUs())
      //  transaction.commit()

                drawerLayout.closeDrawer(GravityCompat.START)
                return true

            }
        })

        linkeden_link.setOnClickListener(View.OnClickListener { object : View.OnClickListener{
            override fun onClick(p0: View?) {
                var i : Intent

            }
        }

        })

    }

    fun loadFragment(fragment: Fragment, fragName: String) {
        toolbar.title = fragName
//        val fm : FragmentManager = supportFragmentManager;
        fm = supportFragmentManager
        val ft : FragmentTransaction = fm.beginTransaction()

        ft.add(R.id.container,fragment)
        ft.commit()
    }


    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START)
        else super.onBackPressed()
    }

}