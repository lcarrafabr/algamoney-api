package com.example.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager; // <<< É quem vai gerenciar a autencicação
	
	
	/**Sobrescrever o configure(ClientDetailsServiceConfigurer clients)*/
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		clients.inMemory()
			.withClient("angular") // Usuario
			.secret("angular01") //Senha
			.scopes("read", "write") //Tipo de leitura
			.authorizedGrantTypes("password") // Não entendi mas parece que é a senha que o angular irá passar ou pegar
			.accessTokenValiditySeconds(1800); // <<< quantos segundos esse token ficara ativo no caso 1800 / 60 = 30 minutos
	}
	
	/**sobrescrever o metodo configure(AuthorizationServerEndpointsConfigurer endpoints)*/
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		endpoints
			.tokenStore(tokenStore())
			.authenticationManager(authenticationManager); //<<< É onde ficará armazenado o token
	}
	
	@Bean
	public TokenStore tokenStore() {
		
		return new InMemoryTokenStore();
	}

}
