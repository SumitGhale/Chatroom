package screen

sealed class Screen(val route: String) {
    object LoginScreen: Screen("loginscreen")
    object signUp: Screen("signup")
    object ChatRoomsScreen:Screen("chatroomscreen")
    object ChatScreen:Screen("chatscreen")
}