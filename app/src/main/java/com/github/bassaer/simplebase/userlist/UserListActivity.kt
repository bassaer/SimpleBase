package com.github.bassaer.simplebase.userlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.bassaer.simplebase.R
import kotlinx.android.synthetic.main.counter_act.*

class UserListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userlist_act)
        setSupportActionBar(toolbar)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.run {
            setDisplayShowHomeEnabled(true)
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.contentFrame, UserListFragment())
        transaction.commit()
    }

}