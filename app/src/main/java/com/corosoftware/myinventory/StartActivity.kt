package com.corosoftware.myinventory

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class StartActivity : AppCompatActivity() {

    var btnLoad:Button? = null
    var btnView:Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btnLoad = findViewById(R.id.bLoad)
        btnView = findViewById(R.id.bView)


        val db = DBHandler(this)
//        db.deleteAllItems()

        val dbCount = db.countItems()

//        // Para obtener el momento actual en el formato deseado
//        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
//        val calendar:Calendar = Calendar.getInstance()
//        val date:String = dateFormat.format(calendar.time)


        // Si la base de datos tiene algo, ve directamente a viewItems
        if (dbCount > 0) startViewItemsActivity()



        btnLoad?.setOnClickListener {
            Toast.makeText(this, "Load", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnView?.setOnClickListener {

            Log.d("DATAdbCount", dbCount.toString());

            if (dbCount > 0) {
                Toast.makeText(this, "DB data loaded", Toast.LENGTH_SHORT).show()

                startViewItemsActivity()

            } else {
                Toast.makeText(this, "No DB data, start from scratch", Toast.LENGTH_SHORT).show()

                //TODO: Agregar un putExtra para indicar a que no existe ningun registro en la db

            }
        }
    }

    public fun startViewItemsActivity() {
        val intent = Intent(this, ViewItemsActivity::class.java)
        startActivity(intent)
    }

}