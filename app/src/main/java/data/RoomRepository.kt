package data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RoomRepository(private val firestore: FirebaseFirestore) {
    suspend fun createRoom(room: Room): OutputResult<Unit> =
        try{
        firestore.collection("rooms").add(room).await()
        OutputResult.Success(Unit)
    }catch (e:Exception){
        OutputResult.Error(e)
    }

    suspend fun getRooms(): OutputResult<List<Room>> =
        try{
            val querySnapshot = firestore.collection("rooms").get().await()
            val rooms = querySnapshot.documents.map { document ->
                document.toObject(Room::class.java)!!.copy(id = document.id)
            }
            OutputResult.Success(rooms)
        }catch (e:Exception){
            OutputResult.Error(e)
        }
}