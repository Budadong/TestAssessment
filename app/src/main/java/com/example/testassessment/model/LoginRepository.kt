package com.example.testassessment.model

class LoginRepository {
    fun authenticate(credentials: UserCredentials): Boolean {
        // In a real app, you'd check against a database or server
        return credentials.user == "johndoe" && credentials.password == "pass123"
    }
}