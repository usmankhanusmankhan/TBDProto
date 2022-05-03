package com.example.tbdproto.data

import com.example.tbdproto.AuthService
import com.example.tbdproto.Singletons
import com.example.tbdproto.data.model.LoggedInUser
import com.google.firebase.auth.FirebaseAuth
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            val auth = Singletons.authInstance
            auth.signIn(username,password)
            if (auth.getUser() != null){
                val user = LoggedInUser(auth.getUid().toString(), auth.getUser()!!)
                return Result.Success(user)
            }
            return Result.Error(IOException("Couldnt find user"))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        val auth = Singletons.authInstance
        auth.logout()

    }
}