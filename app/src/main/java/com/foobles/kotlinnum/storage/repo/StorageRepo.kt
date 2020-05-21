package com.foobles.kotlinnum.storage.repo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await


class StorageRepo {
    val storage = Firebase.storage
    val listRef = storage.reference.child("/data")
    var mAuth = FirebaseAuth.getInstance()

    suspend fun strategiesList(currentUser: FirebaseUser?): ListResult? {
        return try {
            if (currentUser == null)
                mAuth.signInAnonymously().await()

            listRef.listAll().await()
        } catch (e: Exception) {
            null
        }
    }
}