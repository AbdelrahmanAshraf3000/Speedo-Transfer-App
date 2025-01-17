package com.example.speedotransfer.ui.signinandup

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.speedotransfer.ui.PasswordTextFields
import com.example.speedotransfer.R
import com.example.speedotransfer.model.LoginRequestDTO
import com.example.speedotransfer.ui.navigation.Route
import com.example.speedotransfer.ui.TextFields
import com.example.speedotransfer.ui.theme.AppTypography
import com.example.speedotransfer.ui.theme.G0
import com.example.speedotransfer.ui.theme.G100
import com.example.speedotransfer.ui.theme.G40
import com.example.speedotransfer.ui.theme.Login
import com.example.speedotransfer.ui.theme.P300
import com.example.speedotransfer.viewmodel.LoginViewModel


@Composable
fun SignIn(
    navController: NavController, firstTime: Boolean,
    context: Context, viewModelLogin: LoginViewModel = viewModel(), modifier: Modifier = Modifier
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        G0,
                        Login
                    )
                )
            ), contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Sign In",
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                style = AppTypography.titleSemiBold
            )
            Text(
                text = "Speedo Transfer",
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 60.dp),
                style = AppTypography.h2
            )

            SignInFields(navController, context, firstTime, viewModelLogin, modifier = modifier)

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = "Don't have an account?",
                    style = AppTypography.bodyMedium,
                    color = G100
                )
                Text(
                    text = "Sign Up",
                    style = AppTypography.linkMedium,
                    color = P300,
                    modifier = modifier
                        .padding(start = 4.dp)
                        .clickable { navController.navigate(route = Route.SIGNUP) }

                )
            }

        }
    }


}


@Composable
fun SignInFields(
    navController: NavController,
    context: Context,
    firstTime: Boolean,
    viewModelLogin: LoginViewModel,
    modifier: Modifier
) {
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    val signinResponse by viewModelLogin.login.collectAsState()
    val customerResponse by viewModelLogin.customerResponse.collectAsState()
    LaunchedEffect(signinResponse) {
        signinResponse?.let {
            if (signinResponse?.status == "ACCEPTED")
               viewModelLogin.getCustomer(email.value)
            else
                Toast.makeText(navController.context, "Something went wrong", Toast.LENGTH_LONG)
                    .show()
        }
    }
    LaunchedEffect(customerResponse) {
        customerResponse?.let {
            if (it.id != 0) {
                viewModelLogin.saveCustomerData(it)
                navController.navigate("${Route.MAINAPP}/${it.accounts[0].accountName}/${it.accounts[0].balance.toString()}/${it.accounts[0].accountNumber}")
            }
            else
                Toast.makeText(navController.context, "Something went wrong", Toast.LENGTH_LONG)
                    .show()
        }
    }

    Column {
        TextFields("Email", "Enter your email address", email, R.drawable.email, KeyboardType.Email)

        PasswordTextFields("Password", "Enter your password", password)
        Button(
            enabled = email.value.isNotBlank()  && password.value.isNotBlank() && validatePassword(
                password.value
            ) && validateEmail(email.value),
            onClick = {
                if (firstTime) {
                    val writer =
                        context.getSharedPreferences("FirstTime", Context.MODE_PRIVATE).edit()
                    writer.putBoolean("firstTime", false).apply()
                }
                viewModelLogin.login(LoginRequestDTO(email.value, password.value, "296399380"))

            }, modifier = modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .height(50.dp),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(P300)
        ) {
            Text(text = "Sign In ", style = AppTypography.button)
        }
    }
}

/*@Composable
fun TimeOut(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var showNotification by remember { mutableStateOf(true) }

    Box(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        G0,
                        Login
                    )
                )
            ), contentAlignment = Alignment.Center
    ) {
        if (showNotification) {
            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = G40),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 80.dp)
                    .align(Alignment.TopCenter)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .width(300.dp)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.alert),
                        contentDescription = "alert",
                        tint = Color.Unspecified,
                        modifier = modifier
                            .align(Alignment.Top)

                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "We Logged you out because you were inactive for 2 minutes - it’s to help your account secure ",
                        textAlign = TextAlign.Center,
                        style = AppTypography.body2,
                        modifier = modifier.width(230.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = "Cancel",
                        tint = Color.Unspecified,
                        modifier = modifier
                            .clickable { showNotification = false }
                            .align(Alignment.Top)
                    )
                }
            }
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)

        ) {

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 201.dp, bottom = 32.dp)
            ) {
                Text(
                    text = "Welcome Back",
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .fillMaxWidth(),
                    style = AppTypography.h3
                )
                Text(
                    text = "Login to your account",
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(top = 48.dp)
                        .fillMaxWidth(),
                    style = AppTypography.bodyLarge
                )

            }

            SignInFields(
                navController,
                LocalContext.current,
                false,
                viewModelLogin = LoginViewModel(),
                modifier = modifier
            )

        }
    }

}*/


