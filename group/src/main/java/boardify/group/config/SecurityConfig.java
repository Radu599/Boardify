package boardify.group.config;

import boardify.commonsecurity.config.JwtAuthenticationConfig;
import boardify.commonsecurity.filters.authMicroserviceFilters.AuthJwtFilter;
import boardify.commonsecurity.filters.microserviceFilters.JwtTokenAuthenticationFilter;
import boardify.group.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
                .disable()
                .logout().disable()
                .formLogin().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/groups").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .exceptionHandling().authenticationEntryPoint(
                (httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED)
        )
                .and()
                .addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
