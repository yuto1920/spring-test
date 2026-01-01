package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 全てのエンドポイントを対象にする
                .allowedOrigins("https://spring-test-front.vercel.app") // ★ここが重要：VercelのURLだけを許可する
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 許可するHTTPメソッド
                .allowedHeaders("*") // 全てのヘッダーを許可
                .allowCredentials(true); // クッキーなどを含む場合（必要なければfalseでも可）
    }
}