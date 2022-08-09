package com.example.chatapp.ui.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.adapter.ChatAdapter
import com.example.chatapp.common.CommonApp
import com.example.chatapp.common.CommonDataBinding
import com.example.chatapp.databinding.FragmentChatBinding
import com.example.chatapp.model.response.MessageChatResponse
import com.example.chatapp.socket.SocketManager
import com.example.chatapp.ui.base.fragment.BaseFragment
import com.example.chatapp.viewmodel.ChatViewModel

class ChatFragment : BaseFragment(), View.OnClickListener, ChatAdapter.IChat {
    private lateinit var binding: FragmentChatBinding
    private val messages = mutableListOf<MessageChatResponse>()
    private var friendId: Int = 0
    private var friendAvatar = ""
    private var fullName = ""
    private lateinit var viewModel: ChatViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        initData()
        initView()
        register()
        initCallApi()
        return binding.root
    }

    private fun initData() {
        val bundle = this.arguments
        friendId = bundle?.getInt("FRIEND_ID")!!
        friendAvatar = bundle?.getString("FRIEND_AVATAR").toString()
        fullName = bundle?.getString("FRIEND_FULLNAME").toString()
        viewModel = ChatViewModel()
    }

    private fun initView() {
        val llManager = LinearLayoutManager(requireContext())
        llManager.stackFromEnd = true
        binding.rcChat.layoutManager = llManager
        binding.rcChat.adapter = ChatAdapter(this, friendAvatar)

        binding.btnBack.setOnClickListener {
            onBackPress()
        }
        CommonDataBinding.loadNormalImageLink(binding.ivAvatar, friendAvatar)
        binding.tvFullname.text = fullName

        binding.refresh.setOnRefreshListener {
            viewModel.getMessage(friendId)
        }

        binding.btnSend.setOnClickListener(this)
        binding.btnImage.setOnClickListener(this)
    }

    private fun initCallApi() {
        viewModel.getMessage(friendId)
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun register() {
        this.viewModel.getMessagesModel().observe(viewLifecycleOwner) {
            messages.clear()
            messages.addAll(it)
            binding.rcChat.adapter?.notifyDataSetChanged()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.refresh.isRefreshing = it
        }

        SocketManager.getInstance().messageSentModel.observe(getViewLifecycleOwner(), this::sentMessage)
        SocketManager.getInstance().messageReceivedModel.observe(getViewLifecycleOwner(), this::receiveMessage)
    }

    private fun sentMessage(message: MessageChatResponse?) {
        if (message != null && message.receiverId.equals(friendId) && message.senderId.equals(CommonApp.userId)) {
            messages.add(message)
            if (getCount() >1){
                binding.rcChat.adapter?.notifyItemChanged(getCount() - 2)
            }
            binding.rcChat.adapter?.notifyItemInserted(getCount() - 1)
            binding.rcChat.smoothScrollToPosition(getCount() - 1)
        }
    }
    private fun receiveMessage(message: MessageChatResponse?) {
        if (message != null && message.receiverId.equals(CommonApp.userId) && message.senderId.equals(friendId)) {
            messages.add(message)
            if (getCount() >1){
                binding.rcChat.adapter?.notifyItemChanged(getCount() - 2)
            }
            binding.rcChat.adapter?.notifyItemInserted(getCount() - 1)
            binding.rcChat.smoothScrollToPosition(getCount() - 1)
        }
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_send -> {
                sendText()
            }
        }
    }

    override fun getCount() = messages.size

    override fun getItem(position: Int): MessageChatResponse {
        return messages[position]
    }

    private fun sendText() {
        val content = binding.edtContent.text.toString()
        if ("".equals(content)) {
            Toast.makeText(requireContext(), "Please type message", Toast.LENGTH_SHORT).show()
            return
        }
        val message = MessageChatResponse()
        message.content = content
        message.senderId = CommonApp.userId
        message.receiverId = friendId
        message.type = "text"
        SocketManager.getInstance().sendMessage(message)
        binding.edtContent.setText( "")
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}