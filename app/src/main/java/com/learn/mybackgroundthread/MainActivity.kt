package com.learn.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btn_start)
        val tvStatus = findViewById<TextView>(R.id.tv_status)

        btnStart.setOnClickListener {
            try {
                //proes simalasi compres
                for (i in 0..100){
                    Thread.sleep(500)
                    val percentage = i * 10
                    if (percentage == 100){
                        tvStatus.setText(R.string.task_completed)
                    }else{
                        tvStatus.text = String.format(getString(R.string.compressing),percentage)
                    }
                }

            }catch (e: InterruptedException){
                e.printStackTrace()
            }

        }
    }
}