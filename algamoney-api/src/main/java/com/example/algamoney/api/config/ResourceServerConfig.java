package com.example.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration  // A anotação @EnableWebSecurity já possui a @Configuration. Mas coloco para lembrar que é uma classe de configuração
@EnableWebSecurity
@EnableResourceServer //<<<< Incluir para implementar o oauth2
//public class ResourceServerConfig extends WebSecurityConfigurerAdapter{  //<< Antes de implementar o oauth2
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	/**Sobrescrever o metodo configure(AuthenticationManagerBuilder*/
	
	// @Override // <<< Mudar de Override para Autowired
	@Autowired
	//protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	public void configure(AuthenticationManagerBuilder auth) throws Exception { // <<< para usar o oauth2 mudar o metodo para publico
		
		auth.inMemoryAuthentication()
		.withUser("admin").password("{noop}admin").roles("ROLE");
	}
	
	
	/**Sobrescrver o metodo configure(HttpSecurity http)*/
	@Override
	//protected void configure(HttpSecurity http) throws Exception {  // <<< para usar o oauth2 mudar o metodo para publico
	public void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/categorias").permitAll()
		.anyRequest().authenticated()
	.and()
	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Sem manter sessão
	.and()
	.csrf().disable();
		
		
		/**versão antiga antes do oauth
		
		http.authorizeRequests()
			.antMatchers("/categorias").permitAll()
			.anyRequest().authenticated()
		.and().httpBasic()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Sem manter sessão
		.and()
		.csrf().disable();
		
		
		*/
	}
	
	/**Sobrescrever o metodo configure(ResourceServerSecurityConfigurer resources)  quando for usar o oauth*/
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		
		resources.stateless(true);
	}

}
