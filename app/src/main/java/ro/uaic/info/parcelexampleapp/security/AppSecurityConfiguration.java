package ro.uaic.info.parcelexampleapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ro.uaic.info.parcelexampleapp.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class AppSecurityConfiguration {
    @Autowired
    @Lazy
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, EndpointsConfiguration.GET_ADMIN_ONLY).hasAuthority("Admin")
                .antMatchers(HttpMethod.GET, EndpointsConfiguration.GET_DELIVERY_ONLY).hasAuthority("Delivery")
                .antMatchers(HttpMethod.PUT, EndpointsConfiguration.PUT_DELIVERY_ONLY).hasAuthority("Delivery")
                .antMatchers(HttpMethod.GET, EndpointsConfiguration.GET_AUTHENTICATED_ONLY).authenticated()
                .antMatchers(HttpMethod.POST, EndpointsConfiguration.POST_AUTHENTICATED_ONLY).authenticated()
                .antMatchers(HttpMethod.GET, EndpointsConfiguration.GET_UNAUTHENTICATED_ONLY).anonymous()
                .antMatchers(HttpMethod.POST, EndpointsConfiguration.POST_UNAUTHENTICATED_ONLY).anonymous()
                .antMatchers(HttpMethod.GET, EndpointsConfiguration.GET_EVERYONE).permitAll()
                .anyRequest().denyAll()
                .and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic().disable()
                .formLogin().disable()
                .addFilterBefore(new RequestAuthenticationFilter(userService), UsernamePasswordAuthenticationFilter.class)
//                .userDetailsService()
                .build();
    }
}
