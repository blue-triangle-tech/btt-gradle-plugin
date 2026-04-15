package com.bluetriangle.navigation3app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.bluetriangle.navigation3app.ui.theme.BttgradlepluginTheme
import kotlinx.serialization.Serializable

@Serializable
data object ScreenOneKey: NavKey
@Serializable
data object ScreenTwoKey: NavKey
@Serializable
data object ScreenThreeKey: NavKey

class Navigation3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backStack = rememberSaveable { mutableStateListOf<NavKey>(ScreenOneKey) }
            BttgradlepluginTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavDisplay(
                        backStack = backStack,
                        onBack = {
                            backStack.removeLastOrNull()
                        },
                        entryProvider = entryProvider{
                            entry<ScreenOneKey> {
                                ScreenOne(backStack, innerPadding)
                            }
                            entry<ScreenTwoKey> {
                                ScreenTwo(backStack, innerPadding)
                            }
                            entry<ScreenThreeKey> {
                                ScreenThree(backStack, innerPadding)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ScreenOne(backStack: MutableList<NavKey>, innerPadding: PaddingValues) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFDCA367)).padding(innerPadding).padding(40.dp, 100.dp)) {
        Text("Screen One", fontWeight = FontWeight.Black, fontSize = 36.sp)
        Spacer(Modifier.height(64.dp))
        Button(onClick = {
            backStack.add(ScreenTwoKey)
        }) {
            Text("Screen Two")
        }
        Button(onClick = {
            backStack.add(ScreenThreeKey)
        }) {
            Text("Screen Three")
        }
    }
}


@Composable
fun ScreenTwo(backStack: MutableList<NavKey>, innerPadding: PaddingValues) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF79CC7E)).padding(innerPadding).padding(40.dp, 100.dp)) {
        Text("Screen Two", fontWeight = FontWeight.Black, fontSize = 36.sp)
        Spacer(Modifier.height(64.dp))
        Button(onClick = {
            backStack.add(ScreenOneKey)
        }) {
            Text("Screen One")
        }
        Button(onClick = {
            backStack.add(ScreenThreeKey)
        }) {
            Text("Screen Three")
        }
    }
}

@Composable
fun ScreenThree(backStack: MutableList<NavKey>, innerPadding: PaddingValues) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF70A2D9)).padding(innerPadding).padding(40.dp, 100.dp)) {
        Text("Screen Three", fontWeight = FontWeight.Black, fontSize = 36.sp)
        Spacer(Modifier.height(64.dp))
        Button(onClick = {
            backStack.add(ScreenOneKey)
        }) {
            Text("Screen One")
        }
        Button(onClick = {
            backStack.add(ScreenTwoKey)
        }) {
            Text("Screen Two")
        }
    }
}