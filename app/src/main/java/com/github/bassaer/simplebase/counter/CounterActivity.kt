package com.github.bassaer.simplebase.counter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.bassaer.simplebase.R
import kotlinx.android.synthetic.main.counter_act.*

class CounterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.counter_act)
        setSupportActionBar(toolbar)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.run {
            setDisplayShowHomeEnabled(true)
        }
        val userId = intent.getStringExtra(CounterFragment.ARGUMENT_USER_ID)
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = supportFragmentManager.findFragmentById(R.id.contentFrame) as CounterFragment?
        transaction.add(R.id.contentFrame, fragment ?: CounterFragment.newInstance(userId))
        transaction.commit()
    }

}
