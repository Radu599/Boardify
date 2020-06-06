package boardify.gateway.config;

import boardify.commonsecurity.config.JwtAuthenticationConfig;
import boardify.commonsecurity.filters.microserviceFilters.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationConfig config;

    @Autowired
    private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
    }
    @Override
    public void configure(WebSecurity web) {
//        web.ignoring()
//                .antMatchers("**")
// //               .antMatchers("/users/*/register/**") // allow calls to services, redirect by zuul
//                .antMatchers(HttpMethod.OPTIONS, "/**");

    }
}
