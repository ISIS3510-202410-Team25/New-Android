package com.example.foodu.screens.sign_up

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodu.R
import com.example.foodu.components.OutlinedInputText
import com.example.foodu.components.OutlinedPassword
import com.example.foodu.util.rememberImeState

@Composable
fun SignUpScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
    ) {

    val username = viewModel.username.collectAsState()
    val email = viewModel.email.collectAsState()
    val password = viewModel.password.collectAsState()

    // test
    val invalidEmail = viewModel.invalidEmail.collectAsState()
    val invalidPassword = viewModel.invalidPassword.collectAsState()
    val checked = remember { mutableStateOf(false) }

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(scrollState)) {

        // Banner Image
        Image(
            painter = painterResource(R.drawable.salad),
            contentDescription = "Bowl Salad Banner"
        )

        // The rest of the content
        TabRow(selectedTabIndex = 1, containerColor = Color.Transparent) {
            Tab(
                selected = false,
                onClick = { navController.popBackStack() },
                modifier = Modifier.height(56.dp)
            ) {
                Text(text = "Sign In")
            }
            Tab(
                selected = true,
                onClick = { /*TODO*/ },
                modifier = Modifier.height(56.dp)
            ) {
                Text(text = "Sign Up")
            }
        }

        OutlinedInputText(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = username,
            onValueChange = { viewModel.updateUsername(it) },
            label = "Username",
            placeholder = "Enter your name",
            imageVector = Icons.Default.Person,
            contentDescription = "Person Icon",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = username.value.isEmpty()
        )

        OutlinedInputText(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = email,
            onValueChange = { viewModel.updateEmail(it) },
            label = "Email",
            placeholder = "username@example.com",
            imageVector = Icons.Default.Email,
            contentDescription = "Email Icon",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = email.value.isEmpty()
        )

        if (invalidEmail.value) {
            Text(
                text = "Invalid email address.",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp)
            )
        } else {
            Box {

            }
        }

        OutlinedPassword(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = password,
            onValueChange = { viewModel.updatePassword(it) },
            label = "Password",
            placeholder = "Enter your password",
            imageVector = Icons.Default.Lock,
            contentDescription = "Password Icon",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = password.value.isEmpty()
        )

        if (invalidPassword.value) {
            Text(
                text = "Please enter at least 6 characters with digits and letters",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp)
            )
        } else {
            Box {

            }
        }

        Row(modifier = modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = modifier.padding(start = 16.dp, end = 8.dp), text = "Add fingerprint")
            Switch(
                checked = checked.value,
                onCheckedChange = { checked.value = !checked.value }
            )
        }

        Button(
            onClick = { viewModel.onSignUpClick(navController) },
            modifier = modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .height(48.dp),
            enabled = username.value.isNotEmpty() && email.value.isNotEmpty() && password.value.isNotEmpty()
        ) {
            Text("Register")
        }
    }
}