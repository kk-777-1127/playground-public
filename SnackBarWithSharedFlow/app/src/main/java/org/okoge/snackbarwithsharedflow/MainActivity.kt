package org.okoge.snackbarwithsharedflow
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MyApplicationTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // Repositoryのインスタンス化 (本来はDIコンテナで管理)
                val repository = remember { MyRepository() }
                // ViewModelのインスタンス化 (Factoryを使用)
                val myViewModel: MyViewModel = viewModel(
                    factory = MyViewModelFactory(repository)
                )
                SnackbarDemoScreen(myViewModel)
            }
        }
    }
}

@Composable
fun SnackbarDemoScreen(viewModel: MyViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }

    // ViewModelからのSnackbarイベントを監視し、順番に表示
    // LaunchedEffectのキーにUnitを指定すると、コンポジション時に一度だけ実行され、
    // collectは中断されるまでイベントを待ち受け続けます。
    LaunchedEffect(Unit) {
        viewModel.snackbarEvents.collect { message ->
            // showSnackbarはsuspend関数であり、Snackbarが表示されて消えるまで完了を待ちます。
            // これにより、メッセージが順番に表示されます。
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp), // コンテンツ領域のパディング
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Snackbar連続表示デモ",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Button(onClick = {
                viewModel.triggerSequentialMessageEmission(5) // 5つのメッセージをemit
            }) {
                Text("5つのメッセージを連続表示")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.triggerSpecificMessageEmission() // 定義済みメッセージをemit
            }) {
                Text("特定メッセージを連続表示")
            }
        }
    }
}
