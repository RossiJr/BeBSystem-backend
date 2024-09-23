package org.rossijr.bebsystem.configurations;

import org.rossijr.bebsystem.authentication.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Method to configure the security pattern (filter chain) for the application.
     * This configuration includes the following <b>(IN THIS APPLICATION)</b>:
     * <ul>
     *     <li>CSRF protection disabled - As stateless authentication is being used</li>
     *     <li>Authorization for the endpoints - This includes:
     *     <ul>
     *         <li>Not requiring authentication for the authentication endpoint and the validation one</li>
     *         <li>Defining the endpoints which will need authentication</li>
     *     </ul>
     *     <li>Session management - As the application is stateless, the session creation policy is set to STATELESS</li>
     *     <li>Adding the JWT Filter</li>
     * </ul>
     *
     * @param http - The configuration object
     * @return The security filter chain already configured
     * @throws Exception - If there is an error while configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Matches "api/v1/authenticate" or "api/v1/users" endpoints in post requests
                        .requestMatchers(HttpMethod.OPTIONS, "/api/v1/authenticate", "api/v1/authenticate/validate")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/authenticate", "/api/v1/authenticate/validate")
                        .permitAll()
                        .anyRequest().authenticated()          // Require authentication for all other requests
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Method to configure the CORS policy for the application.
     * This configuration includes the following <b>(IN THIS APPLICATION)</b>:
     * <ul>
     *     <li>Allowed origins - Only the localhost:5173 is allowed to access the API</li>
     *     <li>Allowed methods - GET, POST, PUT, DELETE and OPTIONS</li>
     *     <li>Allowed headers - Authorization, Content-Type and Access-Control-Allow-Origin</li>
     *     <li>Allow credentials - If you're using credentials like cookies or tokens</li>
     *     <li>Max age - Time in seconds the response from a preflight request can be cached</li>
     * </ul>
     * @return The configured CORS policy
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v1/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("Authorization", "Content-Type", "Access-Control-Allow-Origin")
                        .allowCredentials(true)  // If you're using credentials like cookies or tokens
                        .maxAge(3600);  // Time in seconds the response from a preflight request can be cached
            }
        };
    }
}
