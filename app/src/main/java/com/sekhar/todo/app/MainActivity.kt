package com.sekhar.todo.app

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.sekhar.todo.app.motivation.MotivationActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
         R.id.motivation -> {
             showMotivation()
             true
         }
            else->{
                super.onOptionsItemSelected(item)
         }
        }
    }
    // move to Motivation screen:
    private fun showMotivation() {
        startActivity(Intent(this@MainActivity, MotivationActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
}
