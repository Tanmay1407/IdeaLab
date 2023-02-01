package com.lnct.ac.`in`.idealab.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.lnct.ac.`in`.idealab.R
import com.lnct.ac.`in`.idealab.frgments.*
import com.lnct.ac.`in`.idealab.quiz.QuizWelcomeFragment

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
                    R.id.home -> loadFragment(HomeFragment(),"Home")
                    R.id.event -> loadFragment(Event(),"Events")
                    R.id.about -> loadFragment(AboutUs(),"About Us")
                    R.id.contact -> loadFragment(ContactUs(),"Contact Us")
                    R.id.highlights -> loadFragment(QuizWelcomeFragment(),"Quiz")
                    R.id.projects -> loadFragment(ProjectFragment(),"Projects")
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
        val fm : FragmentManager = supportFragmentManager;
//        fm = supportFragmentManager
        val ft : FragmentTransaction = fm.beginTransaction()

        val currentFragment: Fragment? = getSupportFragmentManager().findFragmentById(R.id.container)


        if (currentFragment != null) {
            ft.remove(currentFragment)
        }
        ft.add(R.id.container,fragment)
        ft.commit()
    }


    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START)
        else super.onBackPressed()
    }

}