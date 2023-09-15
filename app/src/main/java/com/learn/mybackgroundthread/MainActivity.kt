package com.learn.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btn_start)
        val tvStatus = findViewById<TextView>(R.id.tv_status)

        //peggunaan executor unutk menangani lag pada aplikasi
        /*val executor = Executors.newSingleThreadExecutor()  //newSingleThreadExecutor yang artinya hanya satu thread yang Anda buat.
        val handler =Handler(Looper.getMainLooper())

        btnStart.setOnClickListener {
            //ini adlah penggunaan executor
            executor.execute {
                try {
                    //proes simalasi compres
                    for (i in 0..1000) {
                        Thread.sleep(500)
                        val percentage = i * 10
                        //handler di bawah ini unutk menangani lagging pada aplikasi sebelum nya
                        handler.post {
                            if (percentage == 100) {  //-< ini adlah proses compressing sampai dengan 100%
                                tvStatus.setText(R.string.task_completed)
                            } else {
                                tvStatus.text =
                                    String.format(getString(R.string.compressing), percentage)
                            }
                        }
                    }

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

        }*/
        //selain menggunaan executor dan handler adalah yang lebih baik yaitu  menggunakan couruntine
        //penggunaan couruntine harus mengimpelmentasi kan dulu melalui dependencies
        btnStart.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                //simulate process in background thread
                for (i in 0..10) {
                    delay(500)
                    val percentage = i * 10
                    //Salah satu kelebihan coroutine yaitu kita bisa berpindah antar thread lebih mudah. Di sini kita menggunakan
                    // withContext(Dispatchers.Main) karena kita perlu pindah ke Main Thread untuk update UI berupa TextView, jika tidak menggunakan ini,
                    // maka UI/TextView tidak akan pernah ter-update.
                    withContext(Dispatchers.Main) {
                        //update ui in main thread
                        if (percentage == 100) {
                            tvStatus.setText(R.string.task_completed)
                        } else {
                            tvStatus.text = String.format(getString(R.string.compressing), percentage)
                        }
                    }
                }
            }
        }


    }
}