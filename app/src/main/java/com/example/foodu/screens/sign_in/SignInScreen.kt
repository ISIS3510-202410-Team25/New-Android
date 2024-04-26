package com.example.foodu.screens.sign_in

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodu.R
import com.example.foodu.SIGN_UP_SCREEN
import com.example.foodu.components.OutlinedInputText
import com.example.foodu.components.OutlinedPassword
import com.example.foodu.util.rememberImeState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@Preview(showBackground = true)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: SignInViewModel = hiltViewModel()
) {

    val email = viewModel.email.collectAsState()
    val password = viewModel.password.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    // Input Validation
    val invalidEmail = viewModel.invalidEmail.collectAsState()
    val invalidPassword = viewModel.invalidPassword.collectAsState()

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            viewModel.signInWithGoogleCredential(credential, navController)
        } catch (ex: Exception) {
            Log.d("LOGIN SIGN IN", "Google sign in failed!")
        }
    }
    val token = "834956701513-8i1eqrj1eaalho6ap5cvuth83i404klq.apps.googleusercontent.com"
    val context = LocalContext.current

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Banner Image
        Image(
            painter = painterResource(R.drawable.salad),
            contentDescription = "Bowl Salad Banner"
        )

        // The rest of the content
        TabRow(selectedTabIndex = 0, containerColor = Color.Transparent) {
            Tab(
                selected = true,
                onClick = { /*TODO*/ },
                modifier = Modifier.height(56.dp)
            ) {
                Text(text = "Sign In")
            }
            Tab(
                selected = false,
                onClick = { navController.navigate(route = SIGN_UP_SCREEN) },
                modifier = Modifier.height(56.dp)
            ) {
                Text(text = "Sign Up")
            }
        }

        OutlinedInputText(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = email,
            onValueChange = { viewModel.updateEmail(newEmail = it) },
            label = "Email",
            placeholder = "username@example.com",
            imageVector = Icons.Default.Email,
            contentDescription = "Email Icon",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = email.value.isEmpty()
        )

        if (invalidEmail.value) {
            Text(
                text = "Please enter a valid email",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error,
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
            onValueChange = { viewModel.updatePassword(newPassword = it) },
            label = "Password",
            placeholder = "Enter your password",
            imageVector = Icons.Default.Lock,
            contentDescription = "Password Icon",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = password.value.isEmpty()
        )

        if (invalidPassword.value) {
            Text(
                text = "Please enter at least 6 characters",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp)
            )
        } else {
            Box {

            }
        }
        
//        errorMessage.value?.let {
//            Text(
//                text = "Invalid email or password.",
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.padding(top = 8.dp),
//                textAlign = TextAlign.Center
//            )
//        }
        Button(
            onClick = { viewModel.onSignInClick(navController) },
            modifier = modifier
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .height(48.dp),
            enabled = email.value.isNotEmpty() && password.value.isNotEmpty()
        ) {
            Text("Log in")
        }
        Text(
            text = "Forgot password?",
            modifier = Modifier
                .padding(top = 8.dp, end = 16.dp)
                .align(Alignment.End)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Divider(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                thickness = 1.dp
            )
            Text(
                text = "or",
                textAlign = TextAlign.Center,
                modifier = modifier.padding(horizontal = 8.dp)
            )
            Divider(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                thickness = 1.dp
            )
        }
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            IconButton(
                onClick = {
                    val options = GoogleSignInOptions.Builder(
                        GoogleSignInOptions.DEFAULT_SIGN_IN
                    )
                        .requestIdToken(token)
                        .requestEmail()
                        .build()

                    val googleSignInClient = GoogleSignIn.getClient(context, options)
                    launcher.launch(googleSignInClient.signInIntent)
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.google_icon),
                    contentDescription = "Google Icon",
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                )
            }

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fingerprint),
                    contentDescription = "Fingerprint Login",
                    modifier = Modifier
                        .width(56.dp)
                        .height(56.dp))
            }
        }
    }
}