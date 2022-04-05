package telran.java41.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
//@EnableConfigurationProperties
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailServiceImpl userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.csrf().disable();
		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/account/register/**")
				.permitAll()
			.antMatchers("/forum/posts/**")
				.permitAll()
			.antMatchers(HttpMethod.POST, "/account/login/**")
				.access("@customSecurity.checkDateChangePassword(authentication.name)")			
			.antMatchers(HttpMethod.GET, "/forum/post/{id}/**")
				.access("@customSecurity.checkDateChangePassword(authentication.name)")	
			.antMatchers("/account/user/*/role/*/**")
				.access("@customSecurity.checkDateChangePassword(authentication.name) && hasRole('ADMINISTRATOR')")
			.antMatchers(HttpMethod.PUT, "/account/user/{login}/**")
				.access("@customSecurity.checkDateChangePassword(authentication.name) && #login == authentication.name")
			.antMatchers(HttpMethod.DELETE, "/account/user/{login}/**")
				.access("@customSecurity.checkDateChangePassword(authentication.name) && (#login == authentication.name or hasRole('ADMINISTRATOR'))")
			.antMatchers(HttpMethod.POST, "/forum/post/{author}/**")
				.access("@customSecurity.checkDateChangePassword(authentication.name) && #author == authentication.name")
			.antMatchers(HttpMethod.PUT, "/forum/post/{id}/comment/{author}/**")
				.access("@customSecurity.checkDateChangePassword(authentication.name) && #author == authentication.name")
			.antMatchers(HttpMethod.PUT, "/forum/post/{id}/like/**")
				.access("@customSecurity.checkDateChangePassword(authentication.name) && !@customSecurity.checkPostAuthority(#id, authentication.name)")
			.antMatchers(HttpMethod.PUT, "/forum/post/{id}/**")
				.access("@customSecurity.checkDateChangePassword(authentication.name) && @customSecurity.checkPostAuthority(#id, authentication.name)")			
			.antMatchers(HttpMethod.DELETE, "/forum/post/{id}/**")
				.access("@customSecurity.checkDateChangePassword(authentication.name) && (@customSecurity.checkPostAuthority(#id, authentication.name) or hasRole('MODERATOR'))")
			.anyRequest()
				.authenticated();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService);
	}
}