package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.LivroDAO;
import model.Livro;
import spark.Request;
import spark.Response;


public class LivroService {

	private LivroDAO livroDAO = new LivroDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_TITULO = 2;
	private final int FORM_ORDERBY_PRECO = 3;
	
	
	public LivroService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Livro(), FORM_ORDERBY_TITULO);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Livro(), orderBy);
	}

	
	public void makeForm(int tipo, Livro livro, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umLivro = "";
		if(tipo != FORM_INSERT) {
			umLivro += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umLivro += "\t\t<tr>";
			umLivro += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/livro/list/1\">Novo Livro</a></b></font></td>";
			umLivro += "\t\t</tr>";
			umLivro += "\t</table>";
			umLivro += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/livro/";
			String name, titulo, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Livro";
				titulo = "Duna, Drácula...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + livro.getID();
				name = "Atualizar Livro (ID " + livro.getID() + ")";
				titulo = livro.getTitulo();
				buttonLabel = "Atualizar";
			}
			umLivro += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umLivro += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umLivro += "\t\t<tr>";
			umLivro += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umLivro += "\t\t</tr>";
			umLivro += "\t\t<tr>";
			umLivro += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umLivro += "\t\t</tr>";
			umLivro += "\t\t<tr>";
			umLivro += "\t\t\t<td>&nbsp;Título: <input class=\"input--register\" type=\"text\" name=\"titulo\" value=\""+ titulo +"\"></td>";
			umLivro += "\t\t\t<td>Autor: <input class=\"input--register\" type=\"text\" name=\"autor\" value=\""+ livro.getAutor() +"\"></td>";
			umLivro += "\t\t\t<td>Editora: <input class=\"input--register\" type=\"text\" name=\"editora\" value=\""+ livro.getEditora() +"\"></td>";
			umLivro += "\t\t\t<td>Preço: <input class=\"input--register\" type=\"text\" name=\"preco\" value=\""+ livro.getPreco() +"\"></td>";
			umLivro += "\t\t\t<td>Ano: <input class=\"input--register\" type=\"text\" name=\"ano\" value=\""+ livro.getAno() +"\"></td>";
			umLivro += "\t\t</tr>";
			umLivro += "\t\t<tr>";
			umLivro += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umLivro += "\t\t</tr>";
			umLivro += "\t</table>";
			umLivro += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umLivro += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umLivro += "\t\t<tr>";
			umLivro += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Livro (ID " + livro.getID() + ")</b></font></td>";
			umLivro += "\t\t</tr>";
			umLivro += "\t\t<tr>";
			umLivro += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umLivro += "\t\t</tr>";
			umLivro += "\t\t<tr>";
			umLivro += "\t\t\t<td>&nbsp;Autor: "+ livro.getAutor() +"</td>";
			umLivro += "\t\t\t<td>&nbsp;Título: "+ livro.getTitulo() +"</td>";
			umLivro += "\t\t\t<td>&nbsp;Editora: "+ livro.getEditora() +"</td>";
			umLivro += "\t\t\t<td>Preço: "+ livro.getPreco() +"</td>";
			umLivro += "\t\t\t<td>Ano: "+ livro.getAno() +"</td>";
			umLivro += "\t\t</tr>";
			umLivro += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-LIVRO>", umLivro);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Livros</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/livro/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/livro/list/" + FORM_ORDERBY_TITULO + "\"><b>Título</b></a></td>\n" +
        		"\t<td><a href=\"/livro/list/" + FORM_ORDERBY_PRECO + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Livro> livros;
		if (orderBy == FORM_ORDERBY_ID) {                 	livros = livroDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_TITULO) {		livros = livroDAO.getOrderByTitulo();
		} else if (orderBy == FORM_ORDERBY_PRECO) {			livros = livroDAO.getOrderByPreco();
		} else {											livros = livroDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Livro p : livros) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getID() + "</td>\n" +
            		  "\t<td>" + p.getTitulo() + "</td>\n" +
            		  "\t<td>" + p.getPreco() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/livro/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/livro/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteLivro('" + p.getID() + "', '" + p.getTitulo() + "', '" + p.getPreco() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-LIVRO>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String autor = request.queryParams("autor");
		String titulo = request.queryParams("titulo");
		String editora = request.queryParams("editora");
		float preco = Float.parseFloat(request.queryParams("preco"));
		int ano = Integer.parseInt(request.queryParams("ano"));

		
		String resp = "";
		
		Livro livro = new Livro(-1, autor, titulo, editora, preco, ano);
		
		if(livroDAO.insert(livro) == true) {
            resp = "Livro (" + titulo + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Livro (" + titulo + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Livro livro = (Livro) livroDAO.get(id);
		
		if (livro != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, livro, FORM_ORDERBY_TITULO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Livro " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Livro livro = (Livro) livroDAO.get(id);
		
		if (livro != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, livro, FORM_ORDERBY_TITULO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Livro " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Livro livro = livroDAO.get(id);
        String resp = "";       

        if (livro != null) {
        	livro.setAutor(request.queryParams("autor"));
        	livro.setTitulo(request.queryParams("titulo"));
        	livro.setEditora(request.queryParams("editora"));
        	livro.setPreco(Float.parseFloat(request.queryParams("preco")));
        	livro.setAno(Integer.parseInt(request.queryParams("ano")));
        	livroDAO.update(livro);
        	response.status(200); // success
            resp = "Livro (ID " + livro.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Livro (ID \" + livro.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Livro livro = livroDAO.get(id);
        String resp = "";       

        if (livro != null) {
            livroDAO.delete(id);
            response.status(200); // success
            resp = "Livro (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Livro (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}