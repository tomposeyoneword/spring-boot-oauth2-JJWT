package com.payfone.oauth;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter
{
    // @Autowired
    // private UserDetailsService userDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        //auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        auth.inMemoryAuthentication().withUser("bofa_user").password(passwordEncoder().encode("bofa_user_pw")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        //        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //                .and()
        //                .csrf().disable()
        //                .authorizeRequests()
        //                .antMatchers(HttpMethod.POST, TOKEN_URI).permitAll()
        //                .anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager()))
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, AuthenticationConstants.TOKEN_URI).permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
