package data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository (private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore){
    suspend fun signUp(email: String, password: String, firstName: String, lastName: String): OutputResult<Boolean> =
        try {
            //On success
            auth.createUserWithEmailAndPassword(email, password).await()
            Log.d("signUpMsg", "signInWithEmail:success")
            val user = User(firstName,lastName, email)
            saveUserToFireStore(user = user)
            OutputResult.Success(true)
        }catch (e:Exception){
            //On error
            Log.d("signUpMsg", "error" + e.message)
            OutputResult.Error(e)
        }

    suspend fun saveUserToFireStore(user: User){
        firestore.collection("users").document(user.email).set(user).await()
    }

    suspend fun login(email: String, password: String): OutputResult<Boolean> =
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            OutputResult.Success(true)
        } catch (e: Exception) {
            OutputResult.Error(e)
        }

    suspend fun getCurrentUser(): OutputResult<User> = try {
        val uid = auth.currentUser?.email
        if (uid != null) {
            val userDocument = firestore.collection("users").document(uid).get().await()
            val user = userDocument.toObject(User::class.java)
            if (user != null) {
                Log.d("user2","$uid")
                OutputResult.Success(user)
            } else {
                OutputResult.Error(Exception("User data not found"))
            }
        } else {
            OutputResult.Error(Exception("User not authenticated"))
        }
    } catch (e: Exception) {
        OutputResult.Error(e)
    }
}