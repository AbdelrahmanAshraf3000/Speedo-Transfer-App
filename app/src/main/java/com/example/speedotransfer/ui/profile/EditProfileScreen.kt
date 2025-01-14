package com.example.speedotransfer.ui.profile

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.speedotransfer.R
import com.example.speedotransfer.ui.navigation.Route
import com.example.speedotransfer.ui.CountryPickerField
import com.example.speedotransfer.ui.DatePickerField
import com.example.speedotransfer.ui.TextFields
import com.example.speedotransfer.ui.signinandup.validateEmail
import com.example.speedotransfer.ui.theme.AppTypography
import com.example.speedotransfer.ui.theme.G900
import com.example.speedotransfer.ui.theme.Home
import com.example.speedotransfer.ui.theme.Login
import com.example.speedotransfer.ui.theme.P300

@Composable
fun EditProfileScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val name = "Asmaa Dosuky"
    var nameAccount = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var selectedCountry = remember { mutableStateOf("") }
    var selectedDate = remember { mutableStateOf("") }
    Box(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Home,
                        Login
                    )
                )
            ), contentAlignment = Alignment.Center
    )
    {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.padding(top = 40.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp, 16.dp, 0.dp, 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.drop_down),
                    contentDescription = "back icon",
                    tint = G900,
                    modifier = modifier
                        .clickable { navController.popBackStack()}
                        .size(24.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Edit Profile",
                    style = AppTypography.titleMedium,
                    color = G900,
                )

                Spacer(modifier = Modifier.weight(1f))

            }

            TextFields(inputText = "Full Name", "Enter your Full Name",inputTextField = nameAccount,R.drawable.user,KeyboardType.Unspecified)
            TextFields(inputText = "Email", "Enter your email address",inputTextField = email,R.drawable.email,KeyboardType.Email)
            CountryPickerField(inputText = "Country", "Select your country",inputTextField =selectedCountry)
            DatePickerField(inputText = "Date Of Birth" ,"DD/MM/YYYY" ,inputTextField = selectedDate)
            validateEmail(email.value)
            Button(
                enabled=if(!validateEmail(email.value) && email.value.isNotEmpty() ) false else true,
                onClick = {navController.navigate(Route.SETTINGS) }, modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(P300)
            ) {
                Text(text = "Save", style = AppTypography.button)
            }

        }
    }
}

@Preview
@Composable
private fun EditProfileScreenPreview() {
    EditProfileScreen(rememberNavController())
}