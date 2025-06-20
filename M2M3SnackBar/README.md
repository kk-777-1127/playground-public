```kotlin
SnackbarHost(
    modifier = modifier,
    hostState = snackbarHostState,
) { snackbarData ->
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Snackbar(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .widthIn(max = 700.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                content()
            }
        }
    }
}
```

この構造のSnackBarにたいして、Material2 と Material3 での差分をしりたい。
- material3 と material の で Snackbarが表示できるサンプルを作って 差分を見る
- 特に、Columnの中のTextやIcon が 1行, 2行, 3行, 4行 となった時にどう変わるかみたい