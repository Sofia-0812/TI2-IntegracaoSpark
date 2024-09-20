package dao;

import model.Livro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class LivroDAO extends DAO {	
	public LivroDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Livro livro) {
		boolean status = false;
		try {
			String sql = "INSERT INTO livro (autor, titulo, editora, preco, ano) "
		               + "VALUES ('" + livro.getAutor() + "', '"
		               + livro.getTitulo() + "', '" + livro.getEditora() + "', " + livro.getPreco() + ", " + livro.getAno() + ");";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Livro get(int id) {
		Livro livro = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM livro WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 livro = new Livro(rs.getInt("id"), rs.getString("autor"), rs.getString("titulo"), 
	                				   rs.getString("editora"), 
	        			               (float)rs.getDouble("preco"),
	        			               rs.getInt("ano"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return livro;
	}
	
	
	public List<Livro> get() {
		return get("");
	}

	
	public List<Livro> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Livro> getOrderByTitulo() {
		return get("titulo");		
	}
	
	
	public List<Livro> getOrderByPreco() {
		return get("preco");		
	}
	
	
	private List<Livro> get(String orderBy) {
		List<Livro> livros = new ArrayList<Livro>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM livro" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Livro p = new Livro(rs.getInt("id"), rs.getString("autor"), rs.getString("titulo"), 
     				   rs.getString("editora"), 
		               (float)rs.getDouble("preco"),
		               rs.getInt("ano"));
	            livros.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return livros;
	}
	
	
	public boolean update(Livro livro) {
		boolean status = false;
		try {  
			String sql = "UPDATE livro SET autor = '" + livro.getAutor() + "', "
					   + "titulo = '" + livro.getTitulo() + "', "
					   + "editora = '" + livro.getEditora() + "', " 
					   + "preco = " + livro.getPreco() + ", " 
					   + "ano = " + livro.getAno() + "  WHERE id = " + livro.getID();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM livro WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}