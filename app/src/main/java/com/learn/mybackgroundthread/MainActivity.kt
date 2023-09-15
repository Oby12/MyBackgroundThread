package com.learn.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
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
        val executor = Executors.newSingleThreadExecutor()
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

        }
    }
}