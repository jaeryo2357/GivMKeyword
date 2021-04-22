package com.mut_jaeryo.data.source.user.remote

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.mut_jaeryo.data.R
import com.mut_jaeryo.data.dto.UserModel
import com.mut_jaeryo.data.source.user.UserDataSource
import com.mut_jaeryo.domain.entities.User
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
        @ApplicationContext private val context: Context
) : UserDataSource {
    override suspend fun getUser(): UserModel {
        return UserModel("nothing", 0)
    }

    override suspend fun createUser(user: UserModel) = suspendCancellableCoroutine<Unit> { coroutine ->
        val db = FirebaseFirestore.getInstance().collection("users")
        val doc = db.document(user.name) //고유 id를 자동으로 생성

        doc.get().addOnSuccessListener { document ->
            if (document.exists()) {
                coroutine.cancel(Exception(context.getString(R.string.error_duplicated_user_name)))
            } else {
                val data = hashMapOf(
                        "name" to user.name,
                        "like" to user.like
                )
                doc.set(data)
                        .addOnSuccessListener {
                            coroutine.resume(Unit) {

                            }
                        }
                        .addOnCanceledListener {
                            coroutine.cancel(Exception(context.getString(R.string.error_cancel_create_user)))
                        }
            }
        }.addOnFailureListener { exception ->
            coroutine.cancel(exception)
        }
    }
}