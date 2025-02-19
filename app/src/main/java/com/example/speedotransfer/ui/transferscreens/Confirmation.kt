package com.example.speedotransfer.ui.transferscreens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.speedotransfer.MainActivity
import com.example.speedotransfer.R
import com.example.speedotransfer.model.Transfer
import com.example.speedotransfer.ui.navigation.Route
import com.example.speedotransfer.ui.theme.AppTypography
import com.example.speedotransfer.ui.theme.G100
import com.example.speedotransfer.ui.theme.G30
import com.example.speedotransfer.ui.theme.G40
import com.example.speedotransfer.ui.theme.Home
import com.example.speedotransfer.ui.theme.Login
import com.example.speedotransfer.ui.theme.P300
import com.example.speedotransfer.ui.theme.P50
import com.example.speedotransfer.ui.theme.S400
import com.example.speedotransfer.viewmodel.TransferViewModel

@Composable
fun ConfirmationScreen(navController: NavHostController, amountSent:String, recipientName:String, recipientAccount:String,customerAccountNumber:String, transferViewModel: TransferViewModel =viewModel(), modifier: Modifier = Modifier) {
    val transferResponse by transferViewModel.transferResponse.collectAsState()
    val context = LocalContext.current
    transferResponse?.let {
        if(it.status=="ACCEPTED") {
            navController.navigate("${Route.PAYMENT}/${amountSent}/${recipientName}/${recipientAccount}/${customerAccountNumber}")
            createNotificationChannel(context)
            sendNotification(context,"Transfer Successful","Your transfer was successful")
        }
        else
            Toast.makeText(context, "Transfer failed please try again", Toast.LENGTH_LONG).show()

    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = ScrollState(5), enabled = true)
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Home, Login
                    )
                )
            )
    ) {
        Spacer(modifier = modifier.height(40.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            IconButton(
                modifier = modifier
                    .size(24.dp),
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.drop_down),
                    contentDescription = "back button",
                    modifier = modifier.size(24.dp)
                )

            }
            Text(
                text = "Transfer",
                style = AppTypography.titleMedium,
                modifier = modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        }
        Spacer(modifier = modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.stepper_horizontal_2),
            contentDescription = "",
            modifier = modifier
                .padding(horizontal = 16.dp)
                .size(width = 370.dp, height = 40.dp)
        )

        Spacer(modifier = modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Amount",
                style = AppTypography.bodyMedium
            )
            Text(
                text = "Confirmation",
                style = AppTypography.bodyMedium
            )
            Text(
                text = "Payment",
                style = AppTypography.bodyMedium
            )

        }
        Spacer(modifier = modifier.height(24.dp))
        Text(
            text = "$amountSent EGP", modifier = modifier
                .padding(horizontal = 16.dp), style = AppTypography.titleMedium
        )
        Spacer(modifier = modifier.height(24.dp))

        Text(
            text = "Transfer amount", modifier = modifier
                .padding(horizontal = 16.dp), style = AppTypography.titleMedium
        )
        Spacer(modifier = modifier.height(24.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Total amount", style = AppTypography.bodyMedium)
            Text(text = "$amountSent EGP",style = AppTypography.bodySmall)

        }
        HorizontalDivider(
            color = G40,
            thickness = 1.dp,
            modifier = modifier.padding(horizontal = 16.dp, vertical = 20.dp)
        )
        Box(modifier= modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)){
           Column {

               Card(
                   modifier = modifier
                       .fillMaxWidth()
                       .padding(vertical = 5.dp), colors = CardDefaults.cardColors(
                       P50
                   )
               ) {
                   Row(modifier = modifier.fillMaxWidth()) {

                       Image(

                           painter = painterResource(id = R.drawable.bank),
                           contentDescription = "",
                           modifier = modifier
                               .padding(start = 20.dp)
                               .size(40.dp)
                               .align(Alignment.CenterVertically)
                               .clip(shape = CircleShape)
                               .background(color = G40)


                       )

                       Column(modifier = modifier.padding(start = 40.dp)) {
                           Text(
                               text = "From",
                               style = AppTypography.bodyMedium,
                               color = P300,
                               modifier = modifier.padding(vertical = 16.dp)
                           )
                           Text(text = "Abdelrahman Ashraf", style = AppTypography.titleSemiBold)
                           Text(
                               text = "Account xxxx7890",
                               style = AppTypography.bodySmall,
                               color = G100,
                               modifier = modifier.padding(vertical = 16.dp)
                           )


                       }
                   }

               }
               Card(
                   modifier = modifier
                       .fillMaxWidth()
                       .padding(vertical = 5.dp), colors = CardDefaults.cardColors(
                       P50
                   )
               ) {
                   Row(modifier = modifier.fillMaxWidth()) {

                       Image(
                           alignment = Alignment.Center,
                           painter = painterResource(id = R.drawable.bank),
                           contentDescription = "",
                           modifier = modifier
                               .padding(start = 20.dp)
                               .size(40.dp)
                               .align(Alignment.CenterVertically)
                               .clip(shape = CircleShape)
                               .background(color = G40)
                       )

                       Column(modifier = modifier.padding(start = 40.dp)) {
                           Text(
                               text = "To",
                               style = AppTypography.bodySmall,
                               color = P300,
                               modifier = modifier.padding(vertical = 16.dp)
                           )
                           Text(text = recipientName, style = AppTypography.titleSemiBold)
                           Text(
                               text = "Account xxxx${recipientAccount.takeLast(4)}",
                               style = AppTypography.bodySmall,
                               color = G100,
                               modifier = modifier.padding(vertical = 16.dp)
                           )


                       }
                   }

               }
           }
            Image(
                painter = painterResource(
                    id = R.drawable.transfer_money,
                ),
                contentDescription = "",
                modifier = modifier
                    .align(Alignment.Center)
                    .size(40.dp)
                    .clip(shape = CircleShape)
                    .background(color = S400)

            )
        }
        Spacer(modifier = modifier.height(16.dp))
        Button(
            onClick = {

                transferViewModel.transfer(Transfer(amountSent,recipientAccount))
                }, modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(P300)
        ) {
            Text(text = "Confirm ", style = AppTypography.button)
        }
        Spacer(modifier = modifier.height(16.dp))
        TextButton(
            onClick = {


                navController.navigate(Route.AMONT) },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp),
            border = BorderStroke(1.dp, P300),
            shape = RoundedCornerShape(6.dp),
        ) {
            Text(text = "Previous ", style = AppTypography.button, color = P300)

        }
        Spacer(modifier = modifier.height(58.dp))
    }
}
fun createNotificationChannel(context: Context){
    val channel = NotificationChannel("1","Transfer Notifications",NotificationManager.IMPORTANCE_DEFAULT).apply {
        description="Notification for successful transfers"
    }
    val notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    notificationManager.createNotificationChannel(channel)

}

fun sendNotification(context: Context,notificationTitle:String,notificationBody:String){
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_IMMUTABLE)

    val notificationBuilder= NotificationCompat.Builder(context,"1")
        .setSmallIcon(R.drawable.group_16)
        .setContentTitle(notificationTitle)
        .setContentText(notificationBody)
        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)

    val notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(1,notificationBuilder.build())


}

@Preview
@Composable
private fun ConfirmationScreenPreview() {
    ConfirmationScreen(navController = rememberNavController(),"1000","Omar Khaled","123456789789","123456789789")
}