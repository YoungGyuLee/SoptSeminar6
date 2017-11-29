package com.yg.a6thseminar.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.yg.a6thseminar.ApplicationController
import com.yg.a6thseminar.CommonData
import com.yg.a6thseminar.NetworkService
import com.yg.a6thseminar.Post.LoginResponse
import com.yg.a6thseminar.R
import com.yg.retrofittest.LoginPost
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var networkService: NetworkService? = null
    private val TAG : String = "LOG::Login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = this.resources.getColor(R.color.statusbar)
        }
        login_login_button.setOnClickListener(this)
        networkService = ApplicationController.instance!!.networkService

    }

    override fun onClick(v: View?) {
        when(v){
            login_login_button->{
                login()
            }
        }
    }

    fun login(){
        val loginResponse = networkService!!.login(LoginPost(login_id.text.toString(), login_pw.text.toString()))
        loginResponse.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                if(response!!.isSuccessful){
                    if (response!!.body().msg.equals("success")){
                        CommonData.loginResponse = response!!.body()
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        ApplicationController.instance!!.makeToast("로그인 성공")
                    }else{
                        ApplicationController.instance!!.makeToast("정보를 확인해주세요")
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("통신 상태를 확인해주세요")
            }
        })
    }
}
