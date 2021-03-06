package com.alden.eggincubator.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alden.eggincubator.R
import com.alden.eggincubator.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
lateinit var binding : ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNextWelcome.setOnClickListener {
            startActivity(Intent(this, ChoseEggActivity::class.java))
            finish()
        }

    }
}