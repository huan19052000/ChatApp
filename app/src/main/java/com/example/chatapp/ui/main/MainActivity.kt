package com.example.chatapp.ui.main


import android.os.Bundle
import com.example.chatapp.R
import com.example.chatapp.ui.base.activity.BaseActivity
import com.example.chatapp.ui.base.fragment.BaseFragment
import com.example.chatapp.ui.main.fragment.ChatFragment
import com.example.chatapp.ui.main.fragment.FriendFragment

class MainActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.main,FriendFragment(), FriendFragment::class.java.name).commit()
    }


    fun openChatFragment(friendId:Int, friendAvatar:String,
                         fullName:String,
                         fgHide: BaseFragment
    ){
        val fg = ChatFragment()
        val arg = Bundle()
        arg.putInt("FRIEND_ID", friendId)
        arg.putString("FRIEND_AVATAR", friendAvatar)
        arg.putString("FRIEND_FULLNAME", fullName)
        fg.arguments = arg
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.open_to_left, R.anim.exit_to_left, R.anim.open_to_right, R.anim.exit_to_right)
            .hide(fgHide)
            .add(R.id.main, fg, ChatFragment::class.java.name)
            .addToBackStack(null)
            .commit()
    }

}