package com.jesen.cod.gitappkotlin.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.widget.Switch
import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.view.config.Themer

class RepoDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)
    }
}