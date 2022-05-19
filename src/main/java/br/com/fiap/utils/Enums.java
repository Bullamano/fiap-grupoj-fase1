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
		/**
		 * Ordem ascendente
		 */
		asc,
		/**
		 * Ordem descendente
		 */
		desc,
		/**
		 * A ordem � irrelevante
		 */
		irrelevant
	}
	
	/**
	 * Tipo de link para os Recursos da aplica��o
	 */
	public static enum linkType{
		/**
		 * Links contendo textos para leituras relacionadas ao assunto
		 */
		leitura,
		/**
		 * Links contendo imagens relacionadas ao assunto
		 */
		imagem,
		/**
		 * Links contendo v�deos relacionados ao assunto
		 */
		video
	}

}
