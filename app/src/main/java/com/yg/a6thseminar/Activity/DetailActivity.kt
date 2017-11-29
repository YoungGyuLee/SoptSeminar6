package com.yg.a6thseminar.Activity

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.yg.a6thseminar.ApplicationController
import com.yg.a6thseminar.CommonData
import com.yg.a6thseminar.Get.ContentDetailResponse
import com.yg.a6thseminar.NetworkService
import com.yg.a6thseminar.R
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private var networkService: NetworkService? = null
    private val TAG : String = "LOG::Detail"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = this.resources.getColor(R.color.statusbar)
        }
        networkService = ApplicationController.instance!!.networkService

        val uid = intent.getIntExtra("uid", 0)
        val pid = intent.getIntExtra("pid", 0)
        init(uid, pid)
    }

    fun init(uid : Int, pid : Int){
        val detailResponse = networkService!!.getDetail(CommonData.loginResponse!!.token,
                uid, pid)
        detailResponse.enqueue(object : Callback<ContentDetailResponse>{

            override fun onResponse(call: Call<ContentDetailResponse>?, response: Response<ContentDetailResponse>?) {
                if(response!!.isSuccessful){
                    detail_writer.text = response!!.body().name
                    detail_content.text = response!!.body().content
                    Glide.with(this@DetailActivity)
                            .load(response!!.body().profileImg)
                            .into(detail_image)
                }else{
                    ApplicationController.instance!!.makeToast("못 받음ㅠ")
                }
            }

            override fun onFailure(call: Call<ContentDetailResponse>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("통신 오류")
            }
        })
    }
}
