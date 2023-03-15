package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.nnk.springboot.services.UserService;

/**
 *  Spring Web MVC Security Java Configuration
 * 	Configures authentication and authorization for the application.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	/**
	 * Supports session control to ensure that Spring's security 
	 * session registry is notified when the session is destroyed.
	 *
	 * @return the http session event publisher
	 */
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}

	/**
	 * Password encoder is used to perform a one-way transformation 
	 * of a password to let the password be stored securely that needs 
	 * to be compared to a user-provided password at the time of authentication.
	 *
	 * @return the password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * DaoAuthenticationProvider is an AuthenticationProvider implementation 
	 * that uses a UserDetailsService and PasswordEncoder 
	 * to authenticate a username, password and GrantedAuthority.
	 *
	 * @return the dao authentication provider
	 */
	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	/**
	 * Configure security filters for authentication and authorization.
	 *
	 * @param auth
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	/**
	 * Configures the chain of security filters for authentication and authorization.
	 *
	 * @throws Exception
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers("/admin/**", "user/list").hasRole("ADMIN")
			.antMatchers("/home", "/user/validate", "/user/add", "/js/**", "/css/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.defaultSuccessUrl("/bid/list", true)
			.and()
			.logout()
            .deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.and()
			.exceptionHandling()
			.accessDeniedPage("/403");
	}
}
