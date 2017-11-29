package com.yg.a6thseminar

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by 2yg on 2017. 11. 20..
 */
class ContentViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    //프로필 이름 제목 좋아요 이미지
    var contentImage : ImageView = itemView!!.findViewById(R.id.content_list_image) as ImageView
    var contentName : TextView = itemView!!.findViewById(R.id.content_list_name) as TextView
    var contentTitle : TextView = itemView!!.findViewById(R.id.content_list_title) as TextView
    //var contentLikeCount : TextView = itemView!!.findViewById(R.id.content_list_like_count) as TextView
    //var contentHeart : ImageView = itemView!!.findViewById(R.id.content_list_heart) as ImageView
}