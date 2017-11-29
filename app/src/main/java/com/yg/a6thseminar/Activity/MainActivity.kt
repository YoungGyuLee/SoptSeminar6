package com.yg.a6thseminar.Activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.yg.a6thseminar.*
import com.yg.a6thseminar.Get.ContentResponse
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val REQ_CODE_SELECT_IMAGE = 100
    private var data: Uri? = null
    private var profile_pic: MultipartBody.Part? = null
    private var networkService: NetworkService? = null
    private var contentAdpater : ContentAdapter? = null
    private var requestManager : RequestManager? = null
    private var contentList : ArrayList<ContentResponse>? = null

    private var TAG = "LOG::Main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = this.resources.getColor(R.color.statusbar)
        }
        networkService = ApplicationController.instance!!.networkService
        main_content_list.layoutManager = LinearLayoutManager(this)
        requestManager = Glide.with(this)
        getContent()
        main_write.setOnClickListener {
            startActivity(Intent(applicationContext, WriteActivity::class.java))
        }
        main_renewal.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }

    fun getContent(){
        Log.v(TAG, CommonData.loginResponse!!.uid.toString())
        var getContentList = networkService!!.getContent(CommonData.loginResponse!!.token, CommonData.loginResponse!!.uid)
        getContentList.enqueue(object : Callback<ArrayList<ContentResponse>> {
            override fun onResponse(call: Call<ArrayList<ContentResponse>>?, response: Response<ArrayList<ContentResponse>>?) {
                if (response!!.isSuccessful) {
                    //pokemonList!!.layoutManager = LinearLayoutManager(this)
                    Log.v(TAG,"success")
                    contentAdpater = ContentAdapter(response.body(), requestManager!!)
                    contentList = response.body()
                    contentAdpater!!.setOnItemClickListener(this@MainActivity)
                    main_content_list.adapter = contentAdpater
                }else{
                    Log.v(TAG,"fail")

                }
            }
            override fun onFailure(call: Call<ArrayList<ContentResponse>>?, t: Throwable?) {
                ApplicationController.instance!!.makeToast("통신 상태를 확인해주세요")
                Log.v(TAG,"checkNetwork")
            }
        })
    }

    override fun onClick(v: View?) {
        val idx : Int = main_content_list.getChildAdapterPosition(v)
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra("uid", contentList!!.get(idx).uid)
        intent.putExtra("pid", contentList!!.get(idx).pid)
        startActivity(intent)
    }

}

