package com.sekhar.todo.app

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.sekhar.todo.app.ShowMotivation.MotivationActivity

class MainActivity : AppCompatActivity() {

    // 1. Permission approach :
    // ask permission to write on storage
    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (!permissions.entries.all { it.value }) {
                Toast.makeText(applicationContext, "Grant permission to use this app", Toast.LENGTH_LONG).show()
                finish()
            }
        }

    private val permissions: Array<String> = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissionsLauncher.launch(permissions)

        // 2. Permission approach :
//        if(ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this@MainActivity, permissions, 100) }

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
