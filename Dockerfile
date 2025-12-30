# --- 1. ビルドを行うステージ ---
# GradleとJava 17が入ったイメージを使います
FROM gradle:8.4.0-jdk17 AS build

# 作業ディレクトリを作成
WORKDIR /app

# プロジェクトのファイルを全てコピー
COPY . .

RUN chmod +x ./gradlew

# アプリをビルド
RUN ./gradlew clean bootJar -x test --no-daemon

# アプリをビルド（テストは時間短縮のためスキップします）
RUN ./gradlew clean bootJar -x test --no-daemon

# --- 2. アプリを実行するステージ ---
# 実行用には軽量なJava 17（Eclipse Temurin）を使います
FROM eclipse-temurin:17-jdk-jammy

# 作業ディレクトリを作成
WORKDIR /app

# ビルドステージで作った jarファイル だけをコピーしてきます
# ※ build/libs/ 以下の .jar ファイルを app.jar という名前に変えてコピー
COPY --from=build /app/build/libs/*.jar app.jar

# 8080ポートを開放することを宣言
EXPOSE 8080

# アプリを実行するコマンド
ENTRYPOINT ["java", "-jar", "app.jar"]