package com.paymentchain.billing.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // URL's that we want to have without security
    private static final String[] NO_AUTH_LIST = {
            "/v3/api-docs",//
            "/configuration/ui", //
            "/swagger-resources", //
            "/configuration/security", //
            "/webjars/**", //
            "/login",
            "/h2-console/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //Choose one configuration
        //01- Full security in order to ask by user and password before to acces swagger ui

//        http
//                .csrf().disable()
//                .authorizeHttpRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic(withDefaults())
//                .formLogin(withDefaults());
//        return http.build();
//    }

        //01.1- Full security in order to ask by user and password before to acces swagger ui: CertiDevs
//
        http
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**").authenticated() // Requiere autenticación para Swagger UI
                        .anyRequest().permitAll() // Permite el acceso sin autenticación al resto de las rutas
                )
                .httpBasic(withDefaults()) // Habilita la autenticación básica HTTP
                .formLogin(withDefaults()); // Habilita el formulario de inicio de sesión por defecto

        return http.build();
//    }

        /**
         * 02- Custom security configuration:
         * - Exclude certain paths and require user authentication (user/password) before accessing Swagger UI HTTP.
         * - CSRF protection is disabled by default to allow POST methods to require authentication.
         * - CSRF/XSRF attacks: Prevent unwanted actions by using tokens (e.g., JWT) on each request.
         */

//        http
//                // CSRF is disabled to allow custom handling via tokens like JWT.
//                .csrf().disable()
//
//                // Configure custom restrictions to enforce user authentication.
//                .authorizeHttpRequests((authz) -> authz
//                        .requestMatchers(NO_AUTH_LIST).permitAll() // Allow paths in NO_AUTH_LIST without authentication.
//                        .requestMatchers(HttpMethod.POST, "/*billing*/**").authenticated() // Require authentication for POST requests to billing paths.
//                        .requestMatchers(HttpMethod.GET, "/*billing*/**").hasRole("ADMIN") // Restrict GET requests to billing paths for ADMIN role.
//                )
//
//                // Use default credentials specified in the .properties file.
//                .httpBasic(withDefaults())
//
//                // Enable the default UI login form.
//                .formLogin(withDefaults());
//
//        return http.build();


    }

    //This Handlers implement the CorsConfigurationSource interface in order to provide a CorsConfiguration for each request.

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cc = new CorsConfiguration();

        cc.setAllowedHeaders(Arrays.asList("Origin,Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization"));
        cc.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));

        cc.setAllowedOrigins(Arrays.asList("/*")); //CAMBIAR EL ORIGEN CUANDO SE SUBA A PRODUCCIÓN, sino sería una brecha de seguridad

        cc.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "PATCH"));

        cc.addAllowedOriginPattern("*");


        cc.setMaxAge(Duration.ZERO);
        cc.setAllowCredentials(Boolean.TRUE);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cc);
        return source;
    }
}
