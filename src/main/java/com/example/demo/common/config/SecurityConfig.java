package com.example.demo.common.config;

import com.example.demo.auth.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .cors().and()

                                .csrf().disable()
                                .authorizeHttpRequests(authz -> authz
                                                // --- Error API 모든 메서드 허용 (맨 위에 배치) ---
                                                .requestMatchers("/api/error-codes/**").permitAll()


                                                // --- Production API 모든 메서드 허용 ---
                                                .requestMatchers("/api/production/**").permitAll()

                                                .requestMatchers(HttpMethod.DELETE, "/api/posts/**").authenticated()
                                                .requestMatchers(HttpMethod.GET, "/api/posts/*/bookmark").permitAll()
                                                .requestMatchers(HttpMethod.PUT, "/api/posts/*/bookmark")
                                                .authenticated()
                                                .requestMatchers(HttpMethod.DELETE, "/api/posts/*/bookmark")
                                                .authenticated()
                                                .requestMatchers(HttpMethod.GET, "/api/posts/*/like").permitAll()
                                                .requestMatchers(HttpMethod.PUT, "/api/posts/*/like").authenticated()
                                                .requestMatchers(HttpMethod.DELETE, "/api/posts/*/like").authenticated()
                                                .requestMatchers(HttpMethod.GET, "/api/posts/*/comments").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/posts/*/comments")
                                                .authenticated()
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/posts/*/view").permitAll()
                                                .requestMatchers("/api/auth/**").permitAll()
                                                .requestMatchers("/api/public/**").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/posts", "/api/posts/*",
                                                                "/api/posts/**")
                                                .permitAll()
                                                .requestMatchers("/api/schedules/**").authenticated()

                                                .requestMatchers(HttpMethod.POST, "/statistics/upload").permitAll()

                                                // --- 추가: /process 라우팅 규칙 ---
                                                .requestMatchers(HttpMethod.GET, "/api/statistics/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/statistics/**").permitAll()
                                                .requestMatchers(HttpMethod.OPTIONS, "/api/statistics/**").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/process/routings/**").permitAll()
                                                // validate는 공개 (원하면 authenticated로 바꿔도 됨)
                                                .requestMatchers(HttpMethod.POST, "/process/routings/*/validate").permitAll()
                                                // 저장(치환)은 인증 필요
                                                .requestMatchers(HttpMethod.PUT, "/process/routings/**").authenticated()
                                                .requestMatchers(HttpMethod.GET, "/api/position/stream").permitAll() // ★ 임시 개방

                                                .requestMatchers(org.springframework.http.HttpMethod.GET, "/process/routings/car/**").permitAll()
                                                .requestMatchers(org.springframework.http.HttpMethod.GET, "/process/routings/car").permitAll()
                                                .requestMatchers(org.springframework.http.HttpMethod.GET, "/process/routings/stations").permitAll()
                                                .requestMatchers(org.springframework.http.HttpMethod.GET, "/process/routings/stations/**").permitAll()

                                                .anyRequest().authenticated())
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
                return authConfig.getAuthenticationManager();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOriginPatterns(Arrays.asList("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                configuration.setAllowCredentials(true);
                configuration.setExposedHeaders(Arrays.asList("Authorization"));

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}