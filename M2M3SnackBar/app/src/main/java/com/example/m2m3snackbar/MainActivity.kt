package com.example.m2m3snackbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.m2m3snackbar.ui.theme.M2M3SnackBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            M2M3SnackBarTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var currentScreen by remember { mutableStateOf("main") }
    
    when (currentScreen) {
        "main" -> {
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                        .windowInsetsPadding(WindowInsets.safeDrawing),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Material SnackBar Comparison",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                    
                    Button(
                        onClick = { currentScreen = "material3" },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text("Material 3 SnackBar")
                    }
                    
                    Button(
                        onClick = { currentScreen = "custom_material3" },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text("Custom Material 3 SnackBar")
                    }
                    
                    Button(
                        onClick = { currentScreen = "material2" },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Material 2 SnackBar")
                    }
                }
            }
        }
        "material3" -> {
            Material3SnackBarScreen(
                modifier = Modifier.fillMaxSize()
            )
            
            BackHandler {
                currentScreen = "main"
            }
        }
        "custom_material3" -> {
            CustomMaterial3SnackBarScreen(
                modifier = Modifier.fillMaxSize()
            )
            
            BackHandler {
                currentScreen = "main"
            }
        }
        "material2" -> {
            Material2SnackBarScreen(
                modifier = Modifier.fillMaxSize()
            )
            
            BackHandler {
                currentScreen = "main"
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    M2M3SnackBarTheme {
        MainScreen()
    }
}