package com.example.msystems

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.msystems.App.Companion.CHANNEL_1_ID
import com.example.msystems.databinding.ActivityContainersBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

var COUNT = 0
class Lab5Containers : AppCompatActivity() {
    lateinit var binding: ActivityContainersBinding
    private var flag_date = true
    private var flag_time = true
    private var flag_text = true
    private var flag_web = true
    private var flag_spinner = true
    private lateinit var launcher : ActivityResultLauncher<Intent>
    val descriptions = mutableListOf<String>()
    private lateinit var notificationManager : NotificationManagerCompat
    lateinit var imm: InputMethodManager
    var url = "https://www.google.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContainersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.idNumber.text = Positions.CURRENT.toString() + "/" + Positions.ALL.toString()
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        notificationManager = NotificationManagerCompat.from(this)
        for(i in 0 until elements.size) descriptions.add(elements[i].description)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, descriptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.idSpinner.adapter = adapter
        binding.idSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@Lab5Containers, "${p2+1}) ${descriptions[p2]} ${elements[p2].getTime()}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(this@Lab5Containers, "NothingSelected", Toast.LENGTH_SHORT).show()
            }
        }
        binding.show.setOnClickListener(){
            var clicks = 0
            val view = layoutInflater.inflate(R.layout.bottomsheet_fragment, null)
            val dialog = BottomSheetDialog(this)
            val head1 = view.findViewById<TextView>(R.id.head1)
            val head2 = view.findViewById<TextView>(R.id.head2)
            val head3 = view.findViewById<TextView>(R.id.head3)
            val head4 = view.findViewById<TextView>(R.id.head4)
            head1.setOnClickListener(){
                Toast.makeText(this, head1.text, Toast.LENGTH_SHORT).show()
            }
            head2.setOnClickListener(){
                Toast.makeText(this, head2.text, Toast.LENGTH_SHORT).show()
            }
            head3.setOnClickListener(){
                Toast.makeText(this, head3.text, Toast.LENGTH_SHORT).show()
            }
            head4.setOnClickListener(){
                Toast.makeText(this, head4.text, Toast.LENGTH_SHORT).show()
            }
            dialog.setContentView(view)
            dialog.show()
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            if(result.resultCode == RESULT_OK){
                Toast.makeText(this, result.data?.getStringExtra("key").toString(), Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.web.goBack()
    }

    fun onClickNext(view: View) {
        startActivity(Intent(this@Lab5Containers, Lab6Lists::class.java))
        Positions.CURRENT++
    }

    fun onClickPrev(view: View) {
        startActivity(Intent(this@Lab5Containers, Lab4Buttons::class.java))
        Positions.CURRENT--
    }

    fun onClickWrite(view: View) {
        if (flag_date) {
            var calendar: Calendar = Calendar.getInstance()
            binding.idTimePicker.visibility = View.GONE
            binding.idEdit.visibility = View.GONE
            binding.web.visibility = View.GONE
            binding.idSpinner.visibility = View.GONE
            binding.idDatePicker.visibility = View.VISIBLE
            binding.idDatePicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ) { view, year, month, day ->
                val month = month + 1
                var t = ""
                t += if(day < 10) "0$day." else "$day."
                t += if(month < 10) "0$month." else "$month."
                t += "$year"
                binding.idDate.text = t
            }
            flag_date = false
            flag_time = true
            flag_text = true
            flag_web = true
            flag_spinner = true
        } else {
            binding.idDatePicker.visibility = View.GONE
            flag_date = true
        }
        imm.hideSoftInputFromWindow(binding.idEdit.windowToken, 0)
    }


    fun onClickTime(view: View) {
        if (flag_time) {
            binding.idDatePicker.visibility = View.GONE
            binding.idEdit.visibility = View.GONE
            binding.web.visibility = View.GONE
            binding.idSpinner.visibility = View.GONE
            binding.idTimePicker.visibility = View.VISIBLE
            binding.idTimePicker.setOnTimeChangedListener { timepicker, hour, minute ->
                var t = ""
                t += if(hour < 10) "0$hour:" else "$hour:"
                t += if(minute < 10) "0$minute" else "$minute"
                binding.idTimer.text = t
            }
            flag_time = false
            flag_date = true
            flag_text = true
            flag_web = true
            flag_spinner = true
        } else {
            binding.idTimePicker.visibility = View.GONE
            flag_time = true
        }
        imm.hideSoftInputFromWindow(binding.idEdit.windowToken, 0)
    }

    fun onClickText(view: View) {
        if (flag_text) {
            binding.idDatePicker.visibility = View.GONE
            binding.idTimePicker.visibility = View.GONE
            binding.web.visibility = View.GONE
            binding.idSpinner.visibility = View.GONE
            binding.idEdit.visibility = View.VISIBLE
            binding.idEdit.setText("")
            imm.showSoftInput(binding.idEdit.rootView, InputMethodManager.SHOW_FORCED)
            flag_text = false
            flag_time = true
            flag_date = true
            flag_web = true
            flag_spinner = true
            binding.idEdit.setOnKeyListener(View.OnKeyListener{v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    imm.hideSoftInputFromWindow(binding.idEdit.windowToken, 0)
                    flag_text = true
                    binding.idEdit.visibility = View.GONE
                    if(binding.idEdit.text.toString() != "") binding.idText.text = binding.idEdit.text
                    return@OnKeyListener true
                }
                false
            })
        } else {
            imm.hideSoftInputFromWindow(binding.idEdit.windowToken, 0)
            flag_text = true
            binding.idEdit.visibility = View.GONE
            binding.idEdit.setText("")
        }
    }

    fun onClickStack(view: View) {
        startActivity(Intent(this@Lab5Containers, Lab5Recycler::class.java))
    }

    fun onClickYouTube(view: View) {
        if(flag_web) {
            binding.idDatePicker.visibility = View.GONE
            binding.idTimePicker.visibility = View.GONE
            binding.idEdit.visibility = View.GONE
            binding.idSpinner.visibility = View.GONE
            binding.web.visibility = View.VISIBLE
            binding.web.loadUrl(url)
            var webSettings = binding.web.settings
            webSettings.javaScriptEnabled = true
            webSettings.loadWithOverviewMode = true
            flag_text = true
            flag_time = true
            flag_date = true
            flag_web = false
            flag_spinner = true
            binding.web.webViewClient  = object : WebViewClient(){
                var currentUrl = ""
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    currentUrl = url.toString()
                    if(url.startsWith("http") || url.startsWith("https")){
                        return false
                    }
                    if(url.startsWith("intent")){
                        var intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                        var fallBackUrl = intent.getStringExtra("browser_fallback_url")
                        if(fallBackUrl != null) {
                            binding.web.loadUrl(fallBackUrl)
                            return true
                        }
                    }
                    return true
                }
            }
        } else {
            binding.web.visibility = View.GONE
            flag_web = true
        }
    }

    fun onClickPager(view: View) {
        startActivity(Intent(this@Lab5Containers, Num::class.java))
    }

    fun onClickNBook(view: View) {
        startActivity(Intent(this@Lab5Containers, NBook::class.java))
    }

    fun onCLickSnippet(view: View) {
        if(flag_spinner){
            binding.idDatePicker.visibility = View.GONE
            binding.idTimePicker.visibility = View.GONE
            binding.idEdit.visibility = View.GONE
            binding.idSpinner.visibility = View.VISIBLE
            binding.web.visibility = View.GONE
            flag_text = true
            flag_time = true
            flag_date = true
            flag_web = true
            flag_spinner = false
        }else{
            binding.idSpinner.visibility = View.GONE
            flag_spinner = true
        }
    }

    fun onClickLogo(view : View){
        binding.idLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise))
    }

    fun onClickCounter(view: View) {
        launcher?.launch(Intent(this@Lab5Containers, Counter::class.java))
    }

    fun sendOnChannel1(view: View) {
        val activityIntent = Intent(this, Lab5Containers::class.java)
        val contentIntent = PendingIntent.getActivity(
            this,
            0, activityIntent, 0
        )
        val broadcastIntent = Intent(this, NotificationReceiver::class.java)

        broadcastIntent.putExtra("toastMessage", COUNT)
        val actionIntent = PendingIntent.getBroadcast(
            this,
            0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        var notification: Notification = NotificationCompat.Builder(this, CHANNEL_1_ID)
            .setSmallIcon(R.drawable.ic_android)
            .setContentTitle("Notification")
            .setContentText(COUNT.toString())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.BLUE)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .addAction(R.mipmap.ic_launcher, "PLUS", actionIntent)
            .build()
        notificationManager.notify(1, notification)
    }

    class A : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            val message = intent.getStringExtra("toastMessage")
            COUNT++

            Toast.makeText(context, COUNT.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}

