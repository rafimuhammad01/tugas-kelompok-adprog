package com.c2.sisteminformasitugas;

import com.c2.sisteminformasitugas.repository.MatkulRepository;
import com.c2.sisteminformasitugas.security.filter.JWTAuthenticationFilter;
import com.c2.sisteminformasitugas.security.filter.JWTAuthorizationFilter;
import com.c2.sisteminformasitugas.security.provider.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class SisteminformasitugasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SisteminformasitugasApplication.class, args);
	}

	@ComponentScan("com.c2.sisteminformasitugas.service")
	@EnableWebSecurity
	@Configuration
	static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private CustomAuthenticationProvider authProvider;



		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/user/**").permitAll()
					.anyRequest().authenticated()
					.and()
					.addFilterBefore(new JWTAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
					.addFilter(new JWTAuthorizationFilter(authenticationManager()))
					// this disables session creation on Spring Security
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}

		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(authProvider);
		}
	}
}
