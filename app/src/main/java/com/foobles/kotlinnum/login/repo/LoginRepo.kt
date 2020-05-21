package com.foobles.kotlinnum.login.repo

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class LoginRepo {
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    val currentUser by lazy { auth.currentUser }

    fun signOut() = auth.signOut()

    suspend fun createAccount(email: String, password: String): AuthResult? {

        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
//            authResult.user
            authResult
        } catch (e: Exception) {
            null
        }
    }


    suspend fun accessAccount(email: String, password: String): AuthResult? {

        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            authResult
        } catch (e: Exception) {
            null
        }
    }


    suspend fun reload() {
        try {
            auth.currentUser?.reload()?.await()
        } catch (e: Exception) {
        }
    }

}