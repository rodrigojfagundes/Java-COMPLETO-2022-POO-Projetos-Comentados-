package com.nelioalves.workshopmongo.resources.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

//CLASSE URL... basicamente era vai ENCODAR as requisicoes de busca
//exemplo TITULO: "BOM DIA" dai dps de encondar fica BOM%DIA
//ou seja sem o ESPACO
public class URL {
	
	//o metodo para decodificar q recebe o TEXTO q sera aquilo
	//q estamos pesquisando no TITULO dos post
	public static String decodeParam(String text) {
		try {
			return URLDecoder.decode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// caso ocorra algum erro no processo de DECODIFICACAO
			//retornamos a string vazia
			return "";
		}
	}
	
	//METODO para tratar a DATA RECEBIDA, q se chama CONVERTDATE
	//q recebe uma DATE em formato de STRING, e recebe uma data
	//PADRAO caso a conversão de data de erro
	public static Date convertDate(String textDate, Date defaultValue) {
		//instanciando um OBJ do tipo SIMPLEDATEFORMAT chamado de SDF
		//e passando para ele um PADRAO de DATA em ANO MES DIA
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//e no padrao GMT
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			return sdf.parse(textDate);
		} catch (ParseException e) {
			//SE tiver erro na CONVERSAO, nos RETURN a data
			//PADRAO/default
			return defaultValue;
		}
		
	}
}