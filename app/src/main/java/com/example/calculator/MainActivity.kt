package com.example.calculator

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        simpleButtonClickHandler()
        advancedButtonClickHandler()
        aboutMeButtonClickHandler()
        exitButtonClickHandler()
    }

    private fun simpleButtonClickHandler() {
        binding.simpleButton.setOnClickListener {
            startActivity(Intent(this, SimpleCalculatorActivity::class.java))
        }
    }

    private fun advancedButtonClickHandler() {
        binding.advancedButton.setOnClickListener {
            startActivity(Intent(this, AdvancedCalculatorActivity::class.java))
        }
    }

    private fun aboutMeButtonClickHandler() {
        binding.aboutMeButton.setOnClickListener {
            startActivity(Intent(this, AboutMeActivity::class.java))
        }
    }

    private fun exitButtonClickHandler() {
        binding.exitButton.setOnClickListener {
            finishAffinity()
        }
    }
}