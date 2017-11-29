package com.yg.a6thseminar

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.yg.a6thseminar.Get.ContentResponse

/**
 * Created by 2yg on 2017. 11. 20..
 */
class ContentAdapter(var contentList : ArrayList<ContentResponse>, var requestManagaer : RequestManager) : RecyclerView.Adapter<ContentViewHolder>() {


    private var onItemClick : View.OnClickListener? = null

    fun setOnItemClickListener(l:View.OnClickListener){
        onItemClick = l
    }
    override fun getItemCount(): Int = contentList.size

    override fun onBindViewHolder(holder: ContentViewHolder?, position: Int) {
        var holder : ContentViewHolder = holder as ContentViewHolder
        if(contentList.get(position).profileImg == null)
            requestManagaer.load(R.drawable.pic2).into(holder!!.contentImage)
        else
        requestManagaer.load(contentList.get(position).profileImg).into(holder!!.contentImage)
//        if(contentList.get(position).p_isLike == 1)
//            holder!!.contentHeart.setImageResource(R.drawable.filled_pink_heart)

//        holder!!.contentLikeCount.text = "좋아요 : " + contentList.get(position).likeNum.toString()
        holder!!.contentTitle.text = contentList.get(position).title
        holder!!.contentName.text = contentList.get(position).name

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContentViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.content_item, parent, false)
        mainView.setOnClickListener(onItemClick)
        return ContentViewHolder(mainView)
    }
}
