package screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.OutputResult
import viewmodel.AuthViewModel

@Composable
fun SignIn(
    authViewModel: AuthViewModel,
    onNavigatetoSignup: () -> Unit,
    onSignInSuccess:() -> Unit){

    val result by authViewModel.authResult.observeAsState()

    var email by remember { mutableStateOf("145@mailinator.com") }
    var password by remember { mutableStateOf("12345678") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        OutlinedTextField(value = email,
            onValueChange = {email = it},
            label = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))

        OutlinedTextField(value = password,
            onValueChange = {password = it},
            label = { Text(text = "Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))

        Button(onClick = {
            authViewModel.login(email, password)
            when(result){
                is OutputResult.Success ->{
                    onSignInSuccess()
                }
                is OutputResult.Error ->{

                }
                else ->{

                }
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)) {
            Text(text = "Login", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Don't have an account? Sign up.",
            modifier = Modifier.clickable { onNavigatetoSignup() })
    }
}
