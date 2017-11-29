package com.yg.a6thseminar.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yg.a6thseminar.ApplicationController
import com.yg.a6thseminar.CommonData
import com.yg.a6thseminar.NetworkService
import com.yg.a6thseminar.Post.WritePost
import com.yg.a6thseminar.Post.WriteResponse
import com.yg.a6thseminar.R
import kotlinx.android.synthetic.main.activity_write.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteActivity : AppCompatActivity() {

    private var networkService: NetworkService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        networkService = ApplicationController.instance!!.networkService
        write_post.setOnClickListener {
            writeContent()
        }
    }

    fun writeContent(){
        var postContent = networkService!!.postContent(CommonData.loginResponse!!.token, WritePost(
                CommonData.loginResponse!!.uid, write_title.text.toString(), write_content.text.toString()
        ))
        postContent.enqueue(object : Callback<WriteResponse>{
            override fun onResponse(call: Call<WriteResponse>?, response: Response<WriteResponse>?) {
                if (response!!.isSuccessful){
                    if(response!!.body().msg.equals("success")){
                        ApplicationController.instance!!.makeToast("작성 완료")
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    }else{
                        ApplicationController.instance!!.makeToast("작성 실패")
                    }
                }
            }
            override fun onFailure(call: Call<WriteResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("네트워크 상태 확인")

            }
        })
    }
}
