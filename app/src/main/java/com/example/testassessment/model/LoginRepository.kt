package com.example.testassessment.model

class LoginRepository {
    fun authenticate(credentials: UserCredentials): Boolean {
        // In a real app, you'd check against a database or server
        return credentials.email == "email@test.com" && credentials.password == "pass123"
    }
}