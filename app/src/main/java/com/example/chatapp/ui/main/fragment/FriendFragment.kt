package com.example.chatapp.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.adapter.FriendAdapter
import com.example.chatapp.common.CommonApp
import com.example.chatapp.common.CommonDataBinding
import com.example.chatapp.common.SharedPreferencesCommon
import com.example.chatapp.databinding.FragmentFriendBinding
import com.example.chatapp.model.response.FriendResponse
import com.example.chatapp.model.response.LoginResponse
import com.example.chatapp.ui.base.fragment.BaseFragment
import com.example.chatapp.ui.main.MainActivity
import com.example.chatapp.ui.start.StartActivity
import com.example.chatapp.viewmodel.FriendViewModel
import com.example.chatapp.viewmodel.LoginViewModel

class FriendFragment: BaseFragment(), FriendAdapter.IFriendAdapter, View.OnClickListener {
    private lateinit var binding: FragmentFriendBinding
    private lateinit var friendViewModel : FriendViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginResponse: LoginResponse
    private val friendResponses = mutableListOf<FriendResponse>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendBinding.inflate(inflater, container, false)
        initData()
        register()
        //
        binding.myAvatar.setOnClickListener(this)
        friendViewModel.getFriends()
        binding.rcFriend.adapter = FriendAdapter(this)
        binding.rcFriend.layoutManager = LinearLayoutManager(requireContext())
        initViews()
        return binding.root
    }

    private fun initViews(){
        binding.refresh.setOnRefreshListener {
            friendViewModel.getFriends()
        }
        CommonDataBinding.loadNormalImageLink(binding.myAvatar, CommonApp.avatar)
    }


    private fun initData() {
        friendViewModel = FriendViewModel()
        loginViewModel = LoginViewModel()
        loginResponse = LoginResponse()
        binding.data = friendViewModel
    }

    private fun register() {
        friendViewModel.friendsModel.observe(viewLifecycleOwner) {
            friendResponses.clear()
            friendResponses.addAll(it)
            binding.rcFriend.adapter?.notifyDataSetChanged()
        }

        friendViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.refresh.isRefreshing = it
        }
        friendViewModel.errorResponse.observe(viewLifecycleOwner) {
            //roi vao day: mo man hinh Hom
            if (it.status == 401){
                SharedPreferencesCommon.saveUserToken(requireContext(), "")

                val intent = Intent()
                intent.setClass(requireContext(), StartActivity::class.java)
                intent.putExtra("TYPE","OPEN_LOGIN")
                startActivity(intent)

                Toast.makeText(requireContext(),"Token expired", Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }else {
                Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun getCount(): Int {
        return this.friendResponses.size
    }

    override fun getFriend(position: Int): FriendResponse {
        return this.friendResponses[position]
    }

    override fun onClickItem(position: Int) {
        (requireActivity() as MainActivity).openChatFragment(
            getFriend(position).friendId,
            getFriend(position).avatar,
            getFriend(position).firstName+" "+getFriend(position).lastName,
            this
        )
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.my_avatar -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.main, SettingFragment(), SettingFragment::class.java.name)
                    .commit()
            }
        }
    }
}