package com.hardik.easysplitbills

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hardik.easysplitbills.database.attachs.AttachEntity
import com.hardik.easysplitbills.database.attachs.DBAsyncTaskAttach
import com.hardik.easysplitbills.database.expenses.DBAsyncTaskExpense
import com.hardik.easysplitbills.database.expenses.ExpenseEntity
import com.hardik.easysplitbills.database.friends.DBAsyncTaskFriend
import com.hardik.easysplitbills.database.friends.FriendEntity
import com.hardik.easysplitbills.databinding.ActivityMainBinding
import com.hardik.easysplitbills.navfrags.HomeFragment
import com.hardik.easysplitbills.navfrags.OrderFragment
import kotlinx.android.synthetic.main.drawer_header.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var sp : SharedPreferences
    private lateinit var abdt: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private var previous : MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        sp = getSharedPreferences("DataFile",Context.MODE_PRIVATE)
        setUpToolbar()

        binding.navView.setNavigationItemSelectedListener {
            if(previous!=null) {
                previous?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previous = it

            when(it.itemId) {
                R.id.home -> {
                    setUpFragment(HomeFragment(),"Bill Split")
                }
                R.id.orders -> {
                    setUpFragment(OrderFragment(),"Expense History")
                }
                R.id.logout -> {
                    sendAlert()
                }
            }
            binding.drawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.getHeaderView(0).txtUserName.text = sp.getString("UserName","")
        abdt = ActionBarDrawerToggle(
            this@MainActivity,
            binding.drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        binding.drawerLayout.addDrawerListener(abdt)
        abdt.syncState()
        binding.navView.setCheckedItem(R.id.home)
        setUpFragment(HomeFragment(),getString(R.string.app_name))
    }

    private fun setUpFragment(whichFragment : Fragment, whichTitle : String) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frame,
                whichFragment
            )
            .commit()
        supportActionBar?.title = whichTitle
        supportActionBar?.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sendAlert() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Confirmation")
            .setCancelable(false)
            .setMessage("Are You sure you want to log out?")
            .setPositiveButton("Yes") { _, _ ->
                sp.edit().clear().apply()
                DBAsyncTaskFriend(applicationContext, FriendEntity("Random",0.00),4).execute().get()
                DBAsyncTaskAttach(applicationContext, AttachEntity("Unique","Time","Me",0.00),2).execute().get()
                DBAsyncTaskExpense(applicationContext, ExpenseEntity("Time",0.00,"Desc"),2).execute().get()
                val intent = Intent(this@MainActivity, SplashActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No", null)
        val alert = builder.create()
        alert.show()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawers()
        } else {
            when(supportFragmentManager.findFragmentById(R.id.frame)) {
                is HomeFragment -> {
                    super.onBackPressed()
                }
                is OrderFragment -> {
                    setUpFragment(HomeFragment(),"Bill Split")
                }
            }
        }
    }
}
