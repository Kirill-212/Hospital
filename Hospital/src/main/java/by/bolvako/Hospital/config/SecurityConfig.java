package by.bolvako.Hospital.config;

import by.bolvako.Hospital.security.jwt.JwtConfigurer;
import by.bolvako.Hospital.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";// "/api/v1/admin/**"
    private static final String LOGIN_ENDPOINT =  "/api/v1/auth/login";///api/v1/users/
    private static final String USERS_ENDPOINT =  "/api/v1/users/**";
    private static final String DOCTOR_ENDPOINT =  "/api/v1/doctor/**";
    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .antMatchers(USERS_ENDPOINT).hasRole("USER")
                .antMatchers(DOCTOR_ENDPOINT).hasRole("DOCTOR")
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
        http.headers()
                .frameOptions()
                .sameOrigin()
                .cacheControl();
    }
}
