package com.example.chatapp.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.chatapp.R
import java.text.SimpleDateFormat

object CommonDataBinding {
    @JvmStatic
    val FORMAT_TIME = SimpleDateFormat("DD/MM")

    @JvmStatic
    @BindingAdapter(value= ["loadNormalImageLink"])
    fun loadNormalImageLink(img: ImageView, link: String) {
        if (link == null) {
            img.setImageResource(R.drawable.google)
            return
        }
        Glide.with(img.context)
            .load(link)
            .error(R.drawable.google)
            .placeholder(R.drawable.google)
            .into(img)
    }

    @JvmStatic
    @BindingAdapter(value= arrayOf("loadNormalImageResource"))
    fun loadNormalImageResource(img: ImageView, resource: Int?) {
        if (resource == null) {
            img.setImageResource(R.drawable.google)
            return
        }
        Glide.with(img.context)
            .load(resource)
            .error(R.drawable.google)
            .placeholder(R.drawable.google)
            .into(img)
    }
}