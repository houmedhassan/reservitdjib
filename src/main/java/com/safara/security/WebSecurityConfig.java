package com.safara.security;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.safara.security.jwt.JwtAuthEntryPoint;
import com.safara.security.jwt.JwtAuthTokenFilter;




@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true
)
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{

		@Autowired
		UserDetailsServiceImpl userDetailsService;
		
		@Autowired
		private JwtAuthEntryPoint unauthorizedHandler;
		
		@Bean
		public JwtAuthTokenFilter authenticationJwtFilter()
		{
			return new JwtAuthTokenFilter();
		}
		
		@Override
	    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
	        authenticationManagerBuilder
	                .userDetailsService(userDetailsService)
	                .passwordEncoder(passwordEncoder());
	    }
	 
	    @Bean
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
	 
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.cors().disable()
	        	.csrf().disable().
	                authorizeRequests()	                
	                .antMatchers("/authentification/api/**","/external**","/index.html","/**", "/resources/templates/index.html").permitAll()
	                .antMatchers(HttpMethod.GET, "/","*.xls", "/resources/static/**", "/resources/templates/**").permitAll()	             
	                .anyRequest().authenticated()
	                .and()
	                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	        
	        http.addFilterBefore(authenticationJwtFilter(), UsernamePasswordAuthenticationFilter.class);
	    }
		
//	    @Bean
//	    public CorsFilter corsFilter() {
//	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	    	        
//	        final CorsConfiguration config = new CorsConfiguration();
//	        config.setAllowCredentials(true);
//	        config.setAllowedOrigins(Arrays.asList("http://localhost:8100/", "http://localhost:8030/", "https://wahasir.herokuapp.com/"));
//	        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Authorization"));
//	        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//	        
//	        source.registerCorsConfiguration("/**", config);   
//	        return new CorsFilter(source);	        
//	    }
	    
	    @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**")
	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	                .allowedHeaders("*")
	                .allowedOrigins("*");//Credentials(true);
	            }
	        };
	    }
}
