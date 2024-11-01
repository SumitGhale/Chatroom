package viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import data.Injection
import data.Message
import data.MessageRepository
import data.OutputResult
import data.User
import data.UserRepository
import kotlinx.coroutines.launch

class MessageViewModel: ViewModel() {
    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    private val _roomId = MutableLiveData<String>()

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    private val messageRepository: MessageRepository
    private val userRepository: UserRepository

    init {
        messageRepository = MessageRepository(Injection.instance())
        userRepository = UserRepository(FirebaseAuth.getInstance(), Injection.instance())
        loadCurrentUser()
    }

    private fun loadCurrentUser(){
        viewModelScope.launch {
            when(val result = userRepository.getCurrentUser()){
                is OutputResult.Success -> _currentUser.value = result.data
                is OutputResult.Error -> {}
            }
        }
    }

    fun loadMessages(){
        viewModelScope.launch {
            if (_roomId !=null){
                messageRepository.getChatMessages(roomId = _roomId.value.toString())
                    .collect{_messages.value = it}
            }
        }
    }

    fun sendMessage(text: String){
        if(_currentUser.value != null){
            val message = Message(
                senderFirstName = _currentUser.value!!.firstName,
                senderId = _currentUser.value!!.email,
                text = text
            )
            viewModelScope.launch {
                when (messageRepository.sendMessage(_roomId.value.toString(), message)) {
                    is OutputResult.Success -> Unit
                    is OutputResult.Error -> {

                    }
                }
            }
        }
    }
    fun setRoomId(roomId: String) {
        _roomId.value = roomId
        loadMessages()
    }
}