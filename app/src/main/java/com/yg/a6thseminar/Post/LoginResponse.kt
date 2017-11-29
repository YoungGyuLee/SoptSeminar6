package com.yg.a6thseminar.Post

/**
 * Created by 2yg on 2017. 11. 20..
 */
data class LoginResponse (
        var msg : String,
        var uid : Int,
        var profileImg : String,
        var name : String,
        var token : String
)