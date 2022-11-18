package com.example.msystems


import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.msystems.databinding.ActivityLab6ListsBinding
import org.w3c.dom.Document
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL


class Lab6Lists : AppCompatActivity(), ColorAdapter.Listener, DBAdapter.Listener{
    lateinit var binding : ActivityLab6ListsBinding
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var layoutManager_db : RecyclerView.LayoutManager? = null
    private val adapter = ColorAdapter(this)
    private val adapter_db = DBAdapter(this)
    private lateinit var launcher : ActivityResultLauncher<Intent>
    private var color : String = ""
    private lateinit var elem : Elem_color
    private var bln : Boolean = true
    private lateinit var db : DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLab6ListsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        layoutManager = LinearLayoutManager(this)
        binding.idRecyclerView.layoutManager = layoutManager
        binding.idRecyclerView.adapter = adapter

        layoutManager_db = LinearLayoutManager(this)
        binding.db.layoutManager = layoutManager_db
        binding.db.adapter = adapter_db
        binding.idNumber.text = Positions.CURRENT.toString() + "/" + Positions.ALL.toString()
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            if(result.resultCode == RESULT_OK){
                color = result.data?.getStringExtra("key").toString()
                adapter.color(elem, color)
            }
        }
        bln = binding.edit.visibility == View.GONE
        db = DBHelper(this)
//
//        val btn = findViewById<Button>(R.id.AddItem)
//        val info = findViewById<TextView>(R.id.info)
//
//        val policy = ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)
//
//        val url = URL("http://www.cbr.ru/scripts/XML_daily.asp")
//        val client: HttpClient = HttpClient(.newBuilder().build())
//        var result = ""
//        var document: Document? = null
//        try {
//            val response: HttpResponse = client.execute(httpPost)
//            val statusCode: Int = response.getStatusLine().getStatusCode()
//            if (statusCode == 200) {
//                val inputStream: InputStream = response.getEntity().getContent()
//                val reader = BufferedReader(InputStreamReader(inputStream, "windows-1251"))
//                var line: String
//                while (reader.readLine().also { line = it } != null) {
//                    result += line
//                }
//                result = result.substring(45)
//                document = com.example.lab6.ExchangeRate.loadXMLFromString(result)
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        val nameList = document!!.getElementsByTagName("Name")
//        val valueList = document!!.getElementsByTagName("Value")
//        val Names: ArrayList<*> = ArrayList<String>()
//        val Values: ArrayList<*> = ArrayList<String>()
//
//        for (i in 0 until nameList.length) {
//            Names.add(nameList.item(i).textContent)
//            Values.add(valueList.item(i).textContent)
//        }
////
//        //
//        val listView = findViewById<ListView>(R.id.listView)
//        val adapter: com.example.lab6.ExchangeRate.ListAdapter =
//            com.example.lab6.ExchangeRate.ListAdapter(
//                this,
//                R.layout.list_items_exchange_rate,
//                Names,
//                Values
//            )
//        listView.adapter = adapter
//
//        val bNext = findViewById<Button>(R.id.NextPage)
//        bNext.setOnClickListener {
//            val intent = Intent(this@ExchangeRate, NoteList::class.java)
//            startActivity(intent)
//        }

    }

    fun onClickLogo(view : View){
        binding.idLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise))
    }

    override fun onClickColor(elemColor: Elem_color) {
        elem = elemColor
        launcher?.launch(Intent(this@Lab6Lists, Colors::class.java))
    }

    fun onClickAdd(view : View){
        if(bln){
            adapter.add()
        }
        else{
            val write : SQLiteDatabase = db.writableDatabase
            val cv : ContentValues = ContentValues()
            cv.put(DBHelper.KEY_NAME, binding.edit.text.toString())
            write.insert(DBHelper.TABLE_CONTACTS, null, cv)
            db.close()
        }
    }

    fun onClickBln(view: View) {
        if(bln){
            binding.idRecyclerView.visibility = View.GONE
            binding.edit.visibility = View.VISIBLE
            binding.db.visibility = View.VISIBLE
            binding.read.visibility = View.VISIBLE
            binding.clear.visibility = View.VISIBLE
        }
        else{
            binding.idRecyclerView.visibility = View.VISIBLE
            binding.edit.visibility = View.GONE
            binding.db.visibility = View.GONE
            binding.read.visibility = View.GONE
            binding.clear.visibility = View.GONE
        }
        bln = !bln
    }

    fun onClickClear(view: View) {
        val database: SQLiteDatabase = db.writableDatabase
        val cv = ContentValues()
        database.delete(DBHelper.TABLE_CONTACTS, null, null)
        adapter_db.clear()
        db.close()
    }

    fun onClickRead(view: View) {
        val database: SQLiteDatabase = db.writableDatabase
        val cv = ContentValues()
        val cursor: Cursor =
            database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            adapter_db.clear()
            val idIndex: Int = cursor.getColumnIndex(DBHelper.KEY_ID)
            val nameIndex: Int = cursor.getColumnIndex(DBHelper.KEY_NAME)
            do {
                adapter_db.add(cursor.getInt(idIndex), cursor.getString(nameIndex))
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", name = " + cursor.getString(nameIndex))
            } while (cursor.moveToNext())
        } else Log.d("mLog", "0 rows")
        cursor.close()
        db.close()
    }

    override fun del(elem: Elem_db) {
        val database: SQLiteDatabase = db.writableDatabase
        val cv = ContentValues()
        database.execSQL("delete from " + DBHelper.TABLE_CONTACTS + " where " + DBHelper.KEY_ID + " = " + elem.index)
        adapter_db.del(elem)
        db.close()
    }

    fun onClickNext(view : View){
        startActivity(Intent(this@Lab6Lists, Lab7TrafficLight::class.java))
        Positions.CURRENT++
    }

    fun onClickPrev(view: View) {
        startActivity(Intent(this@Lab6Lists, Lab5Containers::class.java))
        Positions.CURRENT--
    }
}