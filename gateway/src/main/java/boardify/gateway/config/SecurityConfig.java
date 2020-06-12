package boardify.gateway.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import boardify.gateway.security.config.JwtConfig;
import boardify.gateway.security.filters.JwtTokenFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfig config;

    @Autowired
    private JwtTokenFilter jwtTokenAuthenticationFilter;

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
