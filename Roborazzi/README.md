# AIへの指示プロンプト

あなたは、モダンなAndroid開発技術に精通したシニアAndroidデベロッパーです。
これから、以下の仕様に厳密に従って、RoborazziによるVisual Regression Testing（VRT）を学習するための、高品質なサンプルAndroidアプリケーションのコードベースをゼロから作成してください。

## 1. プロジェクトの目的と概要

このプロジェクトの目的は、以下のモダンな技術スタックを用いたAndroidアプリ開発のベストプラクティスを、手を動かしながら理解することです。

* **Roborazzi**によるスクリーンショットテスト
* **Jetpack Compose**によるUI構築（Material3を使用）
* **Hilt**による依存性注入（DI）
* **Navigation Compose**による画面遷移
* **マルチモジュールアーキテクチャ**
* **Robolectric**環境下でのUIテストと、**MockK**による依存関係のモック化

## 2. アーキテクチャとモジュール構成

以下の3つのモジュールからなる、クリーンアーキテクチャを意識したマルチモジュール構成とします。

* `feature-home`: ホーム画面に関連するUI、ViewModel、およびそのテストコードを含みます。
* `feature-detail`: 詳細画面に関連するUI、ViewModel、およびそのテストコードを含みます。
* `data-source`: データ取得ロジック（Repository）をカプセル化し、UI層から完全に分離します。

## 3. 使用する主要技術スタック

* **UI:** Jetpack Compose (Material3) ※XML Viewは一切使用しないでください。
* **DI:** Hilt
* **画面遷移:** Navigation Compose
* **テスト:**
    * スクリーンショットテスト: **Roborazzi**
    * テストフレームワーク: **Robolectric**, **JUnit5**
    * モックライブラリ: **MockK**
* **ビルドシステム:** Gradle (Groovyではなく**Kotlin DSL `.kts`** を使用してください)

## 4. 各モジュールの詳細な仕様

### `data-source` モジュール

1.  **データクラス `Item`** を定義してください。
    ```kotlin
    data class Item(val id: String, val name: String, val description: String)
    ```
2.  **`ItemRepository`** という `interface` を作成してください。リスト取得と単体取得の2つのメソッドを持たせます。
    ```kotlin
    interface ItemRepository {
      suspend fun getItems(): Result<List<Item>>
      suspend fun getItem(id: String): Result<Item>
    }
    ```
3.  上記インターフェースの実装クラス **`ItemRepositoryImpl`** を作成し、HiltでBindしてください。この実装は、2〜3個の固定ダミーデータを返すものとします。

### `feature-home` モジュール

1.  `data-source`モジュールに依存します。
2.  **ViewModel (`HomeViewModel`):**
    * `@HiltViewModel` を使用し、`ItemRepository` を注入してください。
    * UIの状態として、`Loading`（読み込み中）、`Success`（成功）、`Error`（失敗）の3つを管理する `UiState` を定義してください。
3.  **UI (`HomeScreen`):**
    * `HomeViewModel`から`UiState`を収集し、状態に応じてUIを切り替えてください。
        * `Loading`: `CircularProgressIndicator` を表示。
        * `Success`: `LazyColumn` を使ってアイテムのリストを表示。各アイテムをタップすると詳細画面に遷移します。
        * `Error`: エラーメッセージとリトライボタンを表示。
4.  **画面遷移:** アイテムをタップすると、Navigation Composeを使って `feature-detail` の画面に `itemId` を渡して遷移してください。

### `feature-detail` モジュール

1.  `data-source`モジュールに依存します。
2.  **ViewModel (`DetailViewModel`):**
    * `@HiltViewModel` を使用し、`ItemRepository` と `SavedStateHandle` を注入して、渡された `itemId` を受け取ってください。
    * `itemId` をもとにリポジトリからアイテム詳細を取得し、UI State（Loading, Success, Error）を管理してください。
3.  **UI (`DetailScreen`):**
    * `UiState`に応じて、アイテムの詳細情報、ローディング表示、またはエラー表示を切り替えてください。

## 5. テストコードの要件

**すべてのUIテストは、Robolectric環境下 (`/src/test`ディレクトリ) で実行するものとします。**

1.  **Hiltのテスト設定:** Hiltがテストで正しく動作するように、`HiltAndroidRule` やカスタムTest Runnerの設定を適切に行ってください。
2.  **Repositoryのモック化:**
    * テスト実行時には、DIコンテナから実際の`ItemRepositoryImpl`をアンインストールし、代わりに**MockKで作成したモック**に差し替えるHiltモジュールをテストコード内に定義してください。
    * `coEvery` を使って、モックが特定のデータ（成功時のデータや、エラー）を返すように振る舞いを定義してください。
3.  **Roborazziテスト:**
    * **a) Preview連携テスト:**
        * `@Preview`で作成したComposableに対し、Roborazzi用のアノテーションを付与する形式のテストを作成してください。
        * `HomeScreen`と`DetailScreen`の両方で、**`Loading`, `Success`, `Error` の各状態に対応するプレビューとテスト**をそれぞれ作成してください。
    * **b) UI操作を伴うシナリオテスト:**
        * `HomeScreen`を表示後、リストの最初のアイテムをクリックし、`DetailScreen`に遷移した後のスクリーンショットを撮影する、というシナリオのテストを `captureRoboImage` を使って作成してください。

## 6. 出力形式

1.  まず、プロジェクト全体の完全なファイル構造を `tree` コマンド形式で出力してください。
2.  次に、各ファイルのフルパスをコメントで明記した上で、そのファイルの内容をすべてコードブロック内に記述してください。
    ```
    // /path/to/file/FileName.kt
    package ...
    
    // file content
    ```

## 7. `README.md` の生成

最後に、以下の内容をすべて含む `README.md` ファイルを生成してください。

* プロジェクトの概要説明
* このプロジェクトから学べることのリスト
* アーキテクチャ図（**Mermaid記法**を使用して図示）
* セットアップとビルド方法
* **Roborazziテストの実行コマンド**
    * 基準画像の保存（レコード）: `./gradlew recordRoborazziDebug`
    * 差分の検証: `./gradlew verifyRoborazziDebug`

---