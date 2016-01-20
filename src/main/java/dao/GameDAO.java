package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Game;
import config.DBConnect;

public class GameDAO implements Serializable{
	
	private static final long serialVersionUID = -1921887186511514285L;

	private Connection DBConn;
	
	private String query;
	
	private PreparedStatement queryExec;
	

	public GameDAO() {
		this.DBConn = null;
		this.query = null;
		this.queryExec = null;
	}
	
	/*
	 * Metodo before e after sao usados para
	 * abrir e fechar conexao
	 */
	public void beforeExecuteQuery() throws SQLException{
		this.DBConn = new DBConnect().getDBConnection();
	}
	
	public void afterExecuteQuery() throws SQLException{
		this.queryExec.close();
		this.DBConn.close();
	}
	
	/**
	 * Lista todas salas
	 * @return todas salas
	 * @throws SQLException
	 */
	public List<Game> getAll() throws SQLException{
		this.beforeExecuteQuery();
		this.query = "SELECT id, name, date_create, num_players, num_max_players"
				+ " FROM game ORDER BY date_create;";
		this.queryExec = this.DBConn.prepareStatement(query);
		ResultSet results = this.queryExec.executeQuery();
		List<Game> roomList = new ArrayList<Game>();
		while (results.next()){
			Game g = new Game();
			g.setId(results.getInt(1));
			g.setName(results.getString(2));
			g.setDateOfCreate(results.getDate(3));
			g.setNumOfPlayers(results.getInt(4));
			g.setNumMaxPlayers(results.getInt(5));
			roomList.add(g);
		}
		this.afterExecuteQuery();
		return roomList;
	}
	
	/**
	 * Consulta a sala pelo id.
	 * @return g sala
	 * @throws SQLException
	 */
	public Game findById(Integer id) throws SQLException{
		this.beforeExecuteQuery();
		this.query = "SELECT id, name, date_create, num_players, num_max_players"
				+ " FROM game WHERE id = ?;";
		this.queryExec = this.DBConn.prepareStatement(query);
		this.queryExec.setInt(1, id);
		ResultSet results = this.queryExec.executeQuery();
		Game g = new Game();
		while (results.next()){
			g.setId(results.getInt(1));
			g.setName(results.getString(2));
			g.setDateOfCreate(results.getDate(3));
			g.setNumOfPlayers(results.getInt(4));
			g.setNumMaxPlayers(results.getInt(5));
		}
		this.afterExecuteQuery();
		return g;
	}
	
	/**
	 * Consulta a sala pelo nome
	 * @return g sala
	 * @throws SQLException
	 */
	public Game findByName(String name) throws SQLException{
		this.beforeExecuteQuery();
		this.query = "SELECT id, name, date_create, num_players, num_max_players"
				+ " FROM game WHERE name = ?;";
		this.queryExec = this.DBConn.prepareStatement(query);
		this.queryExec.setString(1, name);
		ResultSet results = this.queryExec.executeQuery();
		Game g = new Game();
		while (results.next()){
			g.setId(results.getInt(1));
			g.setName(results.getString(2));
			g.setDateOfCreate(results.getDate(3));
			g.setNumOfPlayers(results.getInt(4));
			g.setNumMaxPlayers(results.getInt(5));
		}
		this.afterExecuteQuery();
		return g;
	}
	
	/**
	 * Inserir uma sala no BD
	 * @param g sala
	 * @return msgCode 
	 * @throws SQLException
	 */
	public int insert(Game g) throws SQLException{
		/*
		 * msg code 0 = OK; 1 = Sala ja existe; 2 = Erro no DB 
		 */
		int msgCode = 0;
		try{
			// Consultando se ja existe
			Game room = this.findByName(g.getName());
			if (room == null){
				this.beforeExecuteQuery();
				
				this.query = "INSERT INTO game (name, date_create, num_max_players) VALUES (?,?,?);";
				this.queryExec = this.DBConn.prepareStatement(query);
				this.queryExec.setString(1, g.getName());
				this.queryExec.setDate(2, g.getDateOfCreate());
				this.queryExec.setInt(3, g.getNumMaxPlayers());
				this.queryExec.execute();
				
				this.afterExecuteQuery();
			}else{
				//"Room já existe no sistema";
				msgCode = 1;
			}
			
		}catch (Exception e){
			msgCode = 2;
			System.out.println("Erro ao inserir o registro: :( \n"+e.getMessage());
		}
		return msgCode;
	}
	
	/**
	 * Deleta uma sala
	 * @param g sala
	 * @throws SQLException
	 */
	public void delete(Game g) throws SQLException{
		try{
			this.beforeExecuteQuery();

			this.query = "DELETE FROM game WHERE id = ?;";
			this.queryExec = this.DBConn.prepareStatement(query);
			this.queryExec.setInt(1, g.getId());
			this.queryExec.execute();

			this.afterExecuteQuery();
			
		}catch (Exception e){
			System.out.println("Erro ao excluir o registro: :( \n"+e.getMessage());
		}
	}
	
	/**
	 * Atualiza o numero de player na sala.
	 * Verifica se é para adicionar ou remover o player
	 * 
	 * @param g sala
	 * @param op Operacao de adicionar ou remover
	 * @throws SQLException
	 */
	public void updatePlayersGame(Game g, String op) {
		try{
			int numOfPlayers = this.getNumOfPlayers(g);
			
			if (op.equals("ADD")){
				// Incrementando o numero de player
				numOfPlayers++;
				g.setNumOfPlayers(numOfPlayers);
				this.updateNumOfPlayers(g);
			}else{
				numOfPlayers--;
				// Decrementando, se for igual a 0 a sala eh deletada
				if (numOfPlayers == 0){
					this.delete(g);
				}else{
					g.setNumOfPlayers(numOfPlayers);
					this.updateNumOfPlayers(g);
				}
			}
			
		}catch (Exception e){
			System.out.println("Erro ao excluir o registro: :( \n"+e.getMessage());
		}
	}
	
	/**
	 * Atualiza no banco de dados
	 * @param g
	 * @throws SQLException
	 */
	private void updateNumOfPlayers(Game g) throws SQLException{
		this.beforeExecuteQuery();

		this.query = "UPDATE game SET num_players = ? WHERE id = ?;";
		this.queryExec = this.DBConn.prepareStatement(query);
		
		this.queryExec.setInt(1, g.getNumOfPlayers());
		this.queryExec.setInt(2, g.getId());
		this.queryExec.execute();

		this.afterExecuteQuery();
	}
	
	/**
	 * Atualiza o numero de player na sala
	 * @param g sala
	 * @throws SQLException
	 */
	public int getNumOfPlayers(Game g) throws SQLException{
		try{
			this.beforeExecuteQuery();

			this.query = "SELECT count(*) FROM player WHERE game_play = ?;";
			this.queryExec = this.DBConn.prepareStatement(query);
			this.queryExec.setInt(1, g.getId());
			ResultSet results = this.queryExec.executeQuery();
			int numOfPlayer = 0;
			while (results.next()){
				numOfPlayer = results.getInt(1);
			}
			this.afterExecuteQuery();
			return numOfPlayer;
			
		}catch (Exception e){
			System.out.println("Erro ao consultar o numero de jogadores na sala :( \n"+e.getMessage());
			return -1;
		}
	}

}
