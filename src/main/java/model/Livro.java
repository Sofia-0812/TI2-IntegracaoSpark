package model;


public class Livro {
	private int id;
	private String autor;
	private String titulo;
	private String editora;
	private float preco;
	private int ano;	
	
	public Livro() {
		id = -1;
		autor = "";
		titulo = "";
		editora = "";
		preco = 0.00F;
		ano = 0;
	}

	public Livro(int id, String autor, String titulo, String editora, float preco, int ano) {
		setId(id);
		setAutor(autor);
		setTitulo(titulo);
		setEditora(editora);
		setPreco(preco);
		setAno(ano);
	}		
	
	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}
	
	public float getPreco() {
		return preco;
	}
	
	public void setPreco(float preco) {
		this.preco = preco;
	}

	public int getAno() {
		return ano;
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}


	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Livro:  Autor: " + autor + "   Título: " + titulo + "   Editora: " + editora + "   Preço: R$" + preco + 
				"   Ano: " + ano;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((Livro) obj).getID());
	}	
}