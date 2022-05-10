package br.com.fiap.utils;

/**
 * Classe de Enums para o projeto
 */
public class Enums {
	
	/**
	 * Ordem na qual uma pesquisa ao banco deve ter seus
	 * registros retornados
	 */
	public static enum order{
		asc, desc, irrelevant
	}
	
	/**
	 * Tipo de link para os Recursos da aplicação
	 */
	public static enum linkType{
		leitura, imagem, video
	}

}
