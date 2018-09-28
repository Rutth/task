package com.ruthb.task.views

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.ruthb.task.R
import com.ruthb.task.business.PriorityBusiness
import com.ruthb.task.constants.TaskConstants
import com.ruthb.task.repository.PriorityCacheConstants
import com.ruthb.task.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mPriorityBusiness: PriorityBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        mSecurityPreferences = SecurityPreferences(this)
        mPriorityBusiness = PriorityBusiness(this)

        loadPriorityCache()

        startDefaultFragment()

        formatUserName()
        formatDate()
    }



    private fun loadPriorityCache() {
        PriorityCacheConstants.setCache(mPriorityBusiness.getList())
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null

        when (item.itemId) {
            R.id.nav_done -> {
                fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.COMPLETE)
            }
            R.id.nav_todo -> {
                fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.TODO)
            }
            R.id.nav_logout -> {
                handleLogout()
            }

        }

        if(fragment != null){
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
        }


        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun handleLogout(){

        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_ID)
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_NAME)
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_EMAIL)

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun formatUserName() {
        val name = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)
        val email =  mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_EMAIL)

        val str = "Olá, ${name}!"
        tvHello.text = str

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        val header = navigationView.getHeaderView(0)
        header.tvName.text = name
        header.tvEmail.text = email

    }
    private fun formatDate() {
        val str = "dia semana, dia de month"
        val calendar = Calendar.getInstance()
        println("calendar: $calendar")

        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val month = formatName("MMMM", Calendar.MONTH, calendar.get(Calendar.MONTH)).capitalize()
        val year = calendar.get(Calendar.YEAR)
        val dayOfWeek = formatName("EEEE", Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK)).capitalize()
        println("semana $dayOfWeek")

        tvDate.text = "${dayOfWeek}, ${dayOfMonth} de ${month} de ${year}"

    }

    private fun formatName(format: String, type: Int, n: Int): String{
        val simpleDateMonth = SimpleDateFormat(format, Locale("pt", "BR"))
        val c = GregorianCalendar()
        c.set(type, n)

        val month = simpleDateMonth.format(c.time)

        return month
    }



    private fun startDefaultFragment(){
        var fragment: Fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.COMPLETE)

        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }
}
