package com.example.algamoney.api.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {
	
//	public static void main(String[] args) {
//		
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		
//		System.out.println("Encoder: " + encoder.encode("Mirage2006"));
//		
//	}
	
	public static String encoder(String senha) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
		return encoder.encode(senha);
	}

}
