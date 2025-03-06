package com.example.testassessment.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testassessment.R
import com.example.testassessment.model.Todo
import com.example.testassessment.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePageActivity : AppCompatActivity() {

    private lateinit var apiTextView: TextView
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        apiTextView = findViewById(R.id.apiTextView)
        logoutButton = findViewById(R.id.logoutButton)

        fetchTodos()

        logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun fetchTodos() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.apiService.getTodos()
                if (response.isSuccessful) {
                    val todos = response.body()
                    if (todos != null) {
                        withContext(Dispatchers.Main) {
                            displayTodos(todos)
                        }
                    }
                } else {
                    Log.e("HomePageActivity", "Error fetching todos: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("HomePageActivity", "Exception fetching todos: ${e.message}")
            }
        }
    }

    private fun displayTodos(todos: List<Todo>) {
        val todoText = todos.joinToString("\n") { "${it.id}: ${it.title}" }
        apiTextView.text = todoText
    }

    private fun logout() {
        // Clear any user session data here (e.g., tokens, user IDs)
        // Example:
        // clearSessionData()

        // Navigate to the login activity
        val intent = Intent(this, LoginActivity::class.java)
        // Clear the activity stack so the user can't go back to the homepage
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // Finish the current activity
    }
}