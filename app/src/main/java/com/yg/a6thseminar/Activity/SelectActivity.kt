package com.yg.a6thseminar.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.yg.a6thseminar.R
import kotlinx.android.synthetic.main.activity_select.*

class SelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = this.resources.getColor(R.color.statusbar)
        }

        select_login.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

        select_sign.setOnClickListener {
            startActivity(Intent(applicationContext, SignActivity::class.java))
        }
    }
}
