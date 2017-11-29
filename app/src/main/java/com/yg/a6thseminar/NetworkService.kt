package com.yg.a6thseminar

import com.yg.a6thseminar.Get.ContentDetailResponse
import com.yg.a6thseminar.Get.ContentResponse
import com.yg.a6thseminar.Post.LoginResponse
import com.yg.a6thseminar.Post.WritePost
import com.yg.a6thseminar.Post.WriteResponse
import com.yg.retrofittest.LoginPost
import com.yg.retrofittest.SignResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by 2yg on 2017. 11. 20..
 */
interface NetworkService {
    //회원 가입
    @Multipart
    @POST("appUser/signUp")
    fun sign(@Part("email") email: RequestBody,
             @Part("password") password: RequestBody,
             @Part("name") name: RequestBody,
             @Part profileImg: MultipartBody.Part): Call<SignResponse>

    //로그인
    @POST("appUser/login")
    fun login(@Body loginPost : LoginPost): Call<LoginResponse>

    //글 불러오기
    @GET("appContent/{uid}")
    fun getContent(
            @Header("token") token : String,
            @Path("uid") uid : Int) : Call<ArrayList<ContentResponse>>

    //글 작성
    @POST("appContent")
    fun postContent(
            @Header("token") token : String,
            @Body writePost: WritePost
        ) : Call<WriteResponse>

    //글 상세 내용
    @GET("appContent/info/{uid}/{pid}")
    fun getDetail(
            @Header("token") token : String,
            @Path("uid") uid: Int,
            @Path("pid") pid : Int
    ) : Call<ContentDetailResponse>


}
