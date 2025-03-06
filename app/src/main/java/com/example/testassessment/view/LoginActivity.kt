package com.example.testassessment.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testassessment.R
import com.example.testassessment.databinding.ActivityLoginBinding
import com.example.testassessment.viewmodel.LoginUiState
import com.example.testassessment.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.uiState.observe(this) { state ->
            when (state) {
                is LoginUiState.Success -> {
                    // Navigate to HomePageActivity
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomePageActivity::class.java))
                    finish() // Optional: Finish LoginActivity so the user can't go back
                }

                is LoginUiState.Error -> {
                    binding.errorTextView.text = state.message
                }

                else -> {}
            }
        }
    }
}