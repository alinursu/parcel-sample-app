package ro.uaic.info.parcel.gatewayapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ro.uaic.info.parcel.gatewayapp.client.UserServiceClient;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class AppSecurityConfiguration {
    @Autowired
    @Lazy
    private UserServiceClient userServiceClient;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(HttpMethod.GET, EndpointsConfiguration.GET_ADMIN_ONLY).hasAuthority("Admin")
                                .requestMatchers(HttpMethod.GET, EndpointsConfiguration.GET_DELIVERY_ONLY).hasAuthority("Delivery")
                                .requestMatchers(HttpMethod.PUT, EndpointsConfiguration.PUT_DELIVERY_ONLY).hasAuthority("Delivery")
                                .requestMatchers(HttpMethod.GET, EndpointsConfiguration.GET_AUTHENTICATED_ONLY).authenticated()
                                .requestMatchers(HttpMethod.POST, EndpointsConfiguration.POST_AUTHENTICATED_ONLY).authenticated()
                                .requestMatchers(HttpMethod.GET, EndpointsConfiguration.GET_UNAUTHENTICATED_ONLY).anonymous()
                                .requestMatchers(HttpMethod.POST, EndpointsConfiguration.POST_UNAUTHENTICATED_ONLY).anonymous()
                                .requestMatchers(HttpMethod.GET, EndpointsConfiguration.GET_EVERYONE).permitAll()
                                .anyRequest().denyAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(new RequestAuthenticationFilter(userServiceClient), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
