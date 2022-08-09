package com.example.chatapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.common.CommonApp
import com.example.chatapp.common.CommonDataBinding
import com.example.chatapp.databinding.ItemMessageTextReceiverBinding
import com.example.chatapp.databinding.ItemMessageTextSenderBinding
import com.example.chatapp.model.response.MessageChatResponse

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    companion object {
        val IMAGE_SENDER = 1
        val IMAGE_RECEIVER = 2
        val TEXT_SENDER = 3
        val TEXT_RECEIVER = 4
    }

    private val inter: IChat
    private val friendAvatar: String

    constructor(inter: IChat, friendAvatar: String) {
        this.inter = inter
        this.friendAvatar = friendAvatar
    }

    override fun getItemViewType(position: Int): Int {
        val item = this.inter.getItem(position)
//        if (item.type.equals("image")) {
//            if (item.senderId == CommonApp.userId) {
//                return IMAGE_SENDER
//            } else {
//                return IMAGE_RECEIVER
//            }
//        } else {
            if (item.senderId == CommonApp.userId) {
                return TEXT_SENDER
            } else {
                return TEXT_RECEIVER
            }
   //     }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == TEXT_SENDER) {
            return ItemMessageTextSenderHolder(
                ItemMessageTextSenderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
        val holder = ItemMessageTextReceiverHolder(
            ItemMessageTextReceiverBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        CommonDataBinding.loadNormalImageLink(holder.binding.ivAvatar, friendAvatar)
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = this.inter.getItem(position)
        var avatar: ImageView? = null

        when (getItemViewType(position)) {
            TEXT_SENDER -> {
                (holder as ItemMessageTextSenderHolder).binding.data = item
            }
            else -> {
                (holder as ItemMessageTextReceiverHolder).binding.data = item
                avatar = holder.binding.ivAvatar
            }
        }



        if (avatar != null) {
            if (position == this.inter.getCount() - 1) {
                avatar.visibility = View.VISIBLE
            } else {
                if (item.senderId == this.inter.getItem(position + 1).senderId) {
                    avatar.visibility = View.INVISIBLE
                } else {
                    avatar.visibility = View.VISIBLE
                }
            }
        }
    }


    override fun getItemCount() = this.inter.getCount()

    interface IChat {
        fun getCount(): Int
        fun getItem(position: Int): MessageChatResponse
    }

    class ItemMessageTextSenderHolder(val binding: ItemMessageTextSenderBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ItemMessageTextReceiverHolder(val binding: ItemMessageTextReceiverBinding) :
        RecyclerView.ViewHolder(binding.root)


}