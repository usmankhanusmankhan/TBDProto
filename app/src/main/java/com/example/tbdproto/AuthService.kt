package com.example.tbdproto

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.tbdproto.data.Result
import com.example.tbdproto.data.model.LoggedInUser
import com.google.firebase.auth.FirebaseAuth
import java.io.IOException

class AuthService {
    private val auth = FirebaseAuth.getInstance()

    fun signIn(username: String, password: String): IOException {
        try {
            auth.signInWithEmailAndPassword(username,password)
        } catch (e: Throwable) {
            return IOException("Error logging in", e)
        }
        return IOException("Error logging in")
    }

    fun getUser(): String? {
        try{
            var userName = auth.currentUser!!.email
            return userName
        }catch (e: Throwable) {
            throw IOException("Error logging in", e)
        }
    }

    fun getUid(): String? {
        return auth!!.uid
    }

    fun loginUser(username: String, password: String){

    }

    fun logout(){
        auth.signOut()
    }
}