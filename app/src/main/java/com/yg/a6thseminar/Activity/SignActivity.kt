package com.yg.a6thseminar.Activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.yg.a6thseminar.ApplicationController
import com.yg.a6thseminar.NetworkService
import com.yg.a6thseminar.R
import com.yg.retrofittest.SignResponse
import kotlinx.android.synthetic.main.activity_sign.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class SignActivity : AppCompatActivity() {
    private val REQ_CODE_SELECT_IMAGE = 100
    private var data: Uri? = null
    private var profile_pic: MultipartBody.Part? = null
    private var networkService: NetworkService? = null
    private val TAG : String = "LOG::Sgin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = this.resources.getColor(R.color.statusbar)
        }

        networkService = ApplicationController.instance!!.networkService
        button.setOnClickListener{
            sign()
        }
        image.setOnClickListener {
            changeImage()
        }

    }

    fun changeImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE)
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    //if(ApplicationController.getInstance().is)
                    this.data = data!!.data
                    Log.v("이미지", this.data.toString())

                    val options = BitmapFactory.Options()

                    var input: InputStream? = null // here, you need to get your context.
                    try {
                        input = contentResolver.openInputStream(this.data)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                    val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                    val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                    val photo = File(this.data.toString()) // 가져온 파일의 이름을 알아내려고 사용합니다

                    ///RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
                    // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!
                    profile_pic = MultipartBody.Part.createFormData("profileImg", photo.name, photoBody)
                    //body = MultipartBody.Part.createFormData("image", photo.getName(), profile_pic);

                    Glide.with(this)
                            .load(data.data)
                            .centerCrop()
                            .into(profile)

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    fun sign() {
        val email = RequestBody.create(MediaType.parse("text/pain"), email.text.toString())
        val password = RequestBody.create(MediaType.parse("text/pain"), password.text.toString())
        val name = RequestBody.create(MediaType.parse("text/pain"), name.text.toString())

        val detailResponse = networkService!!.sign(email, password, name, profile_pic!!)
        detailResponse.enqueue(object : Callback<SignResponse> {
            override fun onResponse(call: Call<SignResponse>, response: Response<SignResponse>) {
                if (response.isSuccessful) {
                    ApplicationController.instance!!.makeToast("가입 완료")
                    Log.v(TAG,"가입")
                }else{
                    Log.v(TAG,"가입X")
                }
            }
            override fun onFailure(call: Call<SignResponse>, t: Throwable) {
                Log.v(TAG,"가입X2")
            }
        })
    }
}
