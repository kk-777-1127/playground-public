package org.okoge.snackbarwithsharedflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MyViewModel(private val repository: MyRepository) : ViewModel() {

    // UI (Compose) にSnackbarメッセージを伝えるためのSharedFlow
    // こちらはイベントを伝える目的なので、バッファは小さくても良い
    private val _snackbarEvents = MutableSharedFlow<String>()
    val snackbarEvents: SharedFlow<String> = _snackbarEvents.asSharedFlow()

    init {
        // RepositoryのdataFlowを購読し、受け取った各アイテムに対して副作用（ログ出力とUIへのイベント発行）を実行
        repository.dataFlow
            .onEach { message ->
                // ここで何らかの副作用を実行できます
                // 例えば、ログに出力したり、分析イベントを送信したりなど
                println("ViewModelが受信: $message")

                // Snackbar表示のためにメッセージをUIイベントとして発行
                _snackbarEvents.emit(message)
            }
            .launchIn(viewModelScope) // viewModelScope内でFlowの収集を開始
    }

    /**
     * Repositoryに、指定された数のメッセージを連続してemitするよう指示します。
     */
    fun triggerSequentialMessageEmission(count: Int) {
        viewModelScope.launch {
            repository.emitDataSequentially(count)
        }
    }

    /**
     * Repositoryに、定義済みの複数のメッセージを連続してemitするよう指示します。
     */
    fun triggerSpecificMessageEmission() {
        viewModelScope.launch {
            repository.emitSpecificMessages()
        }
    }
}

// ViewModel Factory (HiltなどのDIライブラリを使用しない場合に必要)
class MyViewModelFactory(private val repository: MyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}