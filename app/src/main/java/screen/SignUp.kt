package screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import viewmodel.AuthViewModel

@Composable

fun SignUpScreen(
    authViewModel: AuthViewModel,
    onNavigateToLogin:() -> Unit
){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        OutlinedTextField(value = email,
            onValueChange = {email = it},
            label = { Text(text = "Email")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))

        OutlinedTextField(value = password,
            onValueChange = {password = it},
            label = { Text(text = "Password")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))

        OutlinedTextField(value = firstName,
            onValueChange = {firstName = it},
            label = { Text(text = "FirstName")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))

        OutlinedTextField(value = lastName,
            onValueChange = {lastName = it},
            label = { Text(text = "LastName")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))

        Button(onClick = {authViewModel.signUp(email, password, firstName,lastName)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)) {
            Text(text = "Sign Up", fontSize = 18.sp)
        }
        Text(text = "Don't have an account? Sign up.",
            modifier = Modifier.clickable { onNavigateToLogin() })
    }
}
