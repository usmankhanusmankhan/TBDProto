package com.example.tbdproto

object Singletons {
    val authInstance: AuthService by lazy() { AuthService() }
}