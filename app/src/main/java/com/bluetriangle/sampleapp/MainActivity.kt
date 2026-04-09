package com.bluetriangle.sampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bluetriangle.sampleapp.ui.theme.BttgradlepluginTheme
import kotlinx.serialization.Serializable

@Serializable
data object ScreenOneDestination
@Serializable
data object ScreenTwoDestination
@Serializable
data object ScreenThreeDestination

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            BttgradlepluginTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController, startDestination = ScreenOneDestination) {
                        composable<ScreenOneDestination> {
                            ScreenOne(navController)
                        }
                        composable<ScreenTwoDestination> {
                            ScreenTwo(navController)
                        }
                        composable<ScreenThreeDestination> {
                            ScreenThree(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenOne(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().background(Color.Magenta)) {
        Text("Screen One")
        Button(onClick = {
            navController.navigate(ScreenTwoDestination)
        }) {
            Text("Screen Two")
        }
        Button(onClick = {
            navController.navigate(ScreenThreeDestination)
        }) {
            Text("Screen Three")
        }
    }
}


@Composable
fun ScreenTwo(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().background(Color.Cyan)) {
        Text("Screen Two")
        Button(onClick = {
            navController.navigate(ScreenOneDestination)
        }) {
            Text("Screen One")
        }
        Button(onClick = {
            navController.navigate(ScreenThreeDestination)
        }) {
            Text("Screen Three")
        }
    }
}

@Composable
fun ScreenThree(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Yellow)) {
        Text("Screen Three")
        Button(onClick = {
            navController.navigate(ScreenOneDestination)
        }) {
            Text("Screen One")
        }
        Button(onClick = {
            navController.navigate(ScreenTwoDestination)
        }) {
            Text("Screen Two")
        }
    }
}