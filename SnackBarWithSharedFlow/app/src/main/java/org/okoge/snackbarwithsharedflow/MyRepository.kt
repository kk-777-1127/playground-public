package org.okoge.snackbarwithsharedflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.random.Random

class MyRepository {
    // バッファ付きのSharedFlow
    // replay = 0: 新しいコレクターは過去のアイテムを受け取らない
    // extraBufferCapacity = 10: 最大10個のアイテムをバッファリング可能。
    // これを超えると、emit処理が中断（suspend）される。
    private val _dataFlow = MutableSharedFlow<String>(replay = 0, extraBufferCapacity = 10)
    val dataFlow: SharedFlow<String> = _dataFlow.asSharedFlow()

    /**
     * 指定された回数だけ、SharedFlowにメッセージをemitします。
     * @param count emitするメッセージの数
     */
    suspend fun emitDataSequentially(count: Int) {
        for (i in 1..count) {
            val message = "メッセージ $i (ID: ${Random.nextInt(1000)})"
            _dataFlow.emit(message)
        }
    }

    /**
     * いくつかの定義済みメッセージを連続してemitします。
     */
    suspend fun emitSpecificMessages() {
        _dataFlow.emit("最初のメッセージです。")
        _dataFlow.emit("これが2番目のメッセージとなります。")
        _dataFlow.emit("最後のメッセージが表示されました！")
    }
}