package com.example.chatapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.common.CommonDataBinding
import com.example.chatapp.databinding.FriendItemBinding
import com.example.chatapp.model.response.FriendResponse
import com.example.chatapp.ui.main.fragment.FriendFragment

class FriendAdapter : RecyclerView.Adapter<FriendAdapter.FriendHolder>{
    private val inter: IFriendAdapter

    constructor(inter: FriendFragment){
        this.inter = inter
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
        return FriendHolder(
            FriendItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendHolder, position: Int) {
        holder.binding.data = inter.getFriend(position)
        holder.binding.tvUsername.setText(
            inter.getFriend(position).firstName+" "+
                    inter.getFriend(position).lastName
        )


        holder.binding.llRoot.setOnClickListener {
            this.inter.onClickItem(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return this.inter.getCount()
    }

    interface IFriendAdapter{
        fun getCount():Int
        fun getFriend(position:Int): FriendResponse
        fun onClickItem(position:Int)
    }
    class FriendHolder(val binding:FriendItemBinding) : RecyclerView.ViewHolder(binding.root)
}