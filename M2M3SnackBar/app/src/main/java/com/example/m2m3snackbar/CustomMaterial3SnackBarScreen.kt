package com.example.m2m3snackbar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.m2m3snackbar.ui.theme.M2M3SnackBarTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomMaterial3SnackBarScreen(modifier: Modifier = Modifier) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showIcon by remember { mutableStateOf(false) }
    
    Scaffold(
        modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing),
        snackbarHost = {
            CustomMaterial3SnackBarHost(
                hostState = snackbarHostState,
                showIcon = showIcon
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .windowInsetsPadding(WindowInsets.systemBars),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Custom Material 3 SnackBar Test",
                style = MaterialTheme.typography.headlineSmall
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Icon:")
                Switch(
                    checked = showIcon,
                    onCheckedChange = { showIcon = it }
                )
                Text(if (showIcon) "ON" else "OFF")
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("1行のテキスト")
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("1行")
                }
                
                Button(
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("1行目のテキスト\n2行目のテキスト")
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("2行")
                }
                
                Button(
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("1行目のテキスト\n2行目のテキスト\n3行目のテキスト")
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("3行")
                }
                
                Button(
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("1行目のテキスト\n2行目のテキスト\n3行目のテキスト\n4行目のテキスト")
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("4行")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomMaterial3SnackBarScreenPreview() {
    M2M3SnackBarTheme {
        CustomMaterial3SnackBarScreen()
    }
}