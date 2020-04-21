package com.omar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.omar.services.UserDetailsServiceImpl;
import com.omar.utils.Utils;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()//
				.headers().disable()//
//				.requiresChannel().anyRequest().requiresSecure()//
				.authorizeRequests()//
				.antMatchers(HttpMethod.POST, SecurityConstants.USER_SIGN_UP_URL).permitAll()//
				.antMatchers(HttpMethod.POST, SecurityConstants.SELLER_SIGN_UP_URL).permitAll()//
				.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_IN_URL).permitAll()//
				.antMatchers(HttpMethod.POST, SecurityConstants.RESET_PASSWORD_URL).permitAll()//
				.antMatchers(HttpMethod.POST, SecurityConstants.FILE_UPLOAD_URL).permitAll()//
				.antMatchers(HttpMethod.GET, SecurityConstants.FILE_GET_URL).permitAll()//
//				.antMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
				.anyRequest().authenticated()//
				.and().addFilter(jwtAuthenticationFilter())//
				.addFilter(jwtAuthorizationFilter())//

				// this disables session creation on Spring Security
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.applyPermitDefaultValues();
		configuration.setAllowedOrigins(Utils.list("*"));
		configuration.setAllowedMethods(Utils.list("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Utils.list("*"));
		configuration.setExposedHeaders(Utils.list("X-Auth-Token", "Authorization", "Access-Control-Allow-Origin",
				"Access-Control-Allow-Credentials"));

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JWTAuthenticationFilter jwtAuth = new JWTAuthenticationFilter();
		jwtAuth.setAuthenticationManager(authenticationManager());
		jwtAuth.setFilterProcessesUrl(SecurityConstants.SIGN_IN_URL);
		return jwtAuth;
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

	@Bean
	JWTAuthorizationFilter jwtAuthorizationFilter() throws Exception {
		JWTAuthorizationFilter jwtAuth = new JWTAuthorizationFilter(authenticationManager());

		return jwtAuth;
	}

}
