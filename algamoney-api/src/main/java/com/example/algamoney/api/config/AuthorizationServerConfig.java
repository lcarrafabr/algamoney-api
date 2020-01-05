package com.example.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

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
		
		/**Esta é a versão com jwt*/
		endpoints
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.authenticationManager(authenticationManager); //<<< É onde ficará armazenado o token
		
		
		/**Usado para guardar o token na memoria. Acima está a versão com jwt*/
		//endpoints
			//.tokenStore(tokenStore())
			//.authenticationManager(authenticationManager); //<<< É onde ficará armazenado o token
	}

	@Bean
	public TokenStore tokenStore() {
		
		//return new InMemoryTokenStore();  // <<< Alterado após usar o jwt
		
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		
		accessTokenConverter.setSigningKey("algaworks"); // <<< Palavra que valida o token (usar algo mais complexo e dificil)
		return accessTokenConverter;
	}

}
