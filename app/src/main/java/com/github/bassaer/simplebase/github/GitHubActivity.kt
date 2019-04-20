package com.github.bassaer.simplebase.github

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.bassaer.simplebase.R
import kotlinx.android.synthetic.main.github_act.*

class GitHubActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.github_act)
        setSupportActionBar(toolbar)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.run {
            setDisplayShowHomeEnabled(true)
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.contentFrame, GitHubFragment())
        transaction.commit()
    }
}