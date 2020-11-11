package com.example.algamoney.api.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FuncoesUtils {
	
	/**
	 * Função que converte datas de string para LocalDate
	 * Formato yyyy-MM-dd HH:mm:ss
	 @param LocalDate data
	  **/
	public static LocalDate converterStringParaLocalDateTime(String data) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateTime = LocalDate.parse(data, formatter);
		
		return dateTime;
	}


}
