package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DBConnect;
import model.Player;


public class PlayerDAO implements Serializable{

	private static final long serialVersionUID = -2298488730467654357L;
	
	private Connection DBConn;
	
	private String query;
	
	private PreparedStatement queryExec;
	
	private GameDAO gameDAO;
	
	public PlayerDAO() {
		super();
		this.DBConn = null;
		this.query = null;
		this.queryExec = null;
		this.gameDAO = new GameDAO();
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
	 * Lista todos players
	 * @return todos players
	 * @throws SQLException
	 */
	public List<Player> getAll() throws SQLException{
		this.beforeExecuteQuery();
		this.query = "SELECT id, name, login, score, date_create, game_room"
				+ " FROM player ORDER BY name;";
		this.queryExec = this.DBConn.prepareStatement(query);
		ResultSet results = this.queryExec.executeQuery();
		List<Player> playerList = new ArrayList<Player>();
		while (results.next()){
			Player p = new Player();
			p.setId(results.getInt(1));
			p.setName(results.getString(2));
			p.setLogin(results.getString(3));
			p.setScore(results.getInt(4));
			p.setDateOfCreate(results.getDate(5));
			p.setGamePlay(this.gameDAO.findById(results.getInt(6)));
			playerList.add(p);
		}
		
		this.afterExecuteQuery();
		return playerList;
	}
	
	/**
	 * usado no converter
	 * @param id id a ser consultado
	 * @return
	 * @throws SQLException
	 */
	public Player findById(Integer id) throws SQLException{
		this.beforeExecuteQuery();
		this.query = "SELECT id, name, login, score, date_create, game_room"
				+ " FROM player WHERE id = ?;";
		this.queryExec = this.DBConn.prepareStatement(query);
		this.queryExec.setInt(1, id);
		ResultSet results = this.queryExec.executeQuery();
		Player p = new Player();
		while (results.next()){
			p.setId(results.getInt(1));
			p.setName(results.getString(2));
			p.setLogin(results.getString(3));
			p.setScore(results.getInt(4));
			p.setDateOfCreate(results.getDate(5));
			p.setGamePlay(this.gameDAO.findById(results.getInt(6)));
		}
		this.afterExecuteQuery();
		return p;
	}
	
	/**
	 * Login do player
	 * @param login login do player
	 * @param password senha
	 * @return player
	 * @throws SQLException
	 */
	public Player doLogin(String login, String password) throws SQLException{
		this.beforeExecuteQuery();
		this.query = "SELECT id, name, login, score, date_create, game_room "
				+ "FROM player WHERE login = ? AND password = ?;";
		this.queryExec = this.DBConn.prepareStatement(query);
		this.queryExec.setString(1, login);
		this.queryExec.setString(2, password);
		ResultSet results = this.queryExec.executeQuery();
		Player p = new Player();
		while (results.next()){
			p.setId(results.getInt(1));
			p.setName(results.getString(2));
			p.setLogin(results.getString(3));
			p.setScore(results.getInt(4));
			p.setDateOfCreate(results.getDate(5));
			p.setGamePlay(this.gameDAO.findById(results.getInt(6)));
		}
		this.afterExecuteQuery();
		return p;
	}
	
	/**
	 * Consulta pelo login. Usado para verificar se ja existe 
	 * no banco de dados na hora da insercao.
	 * 
	 * @param login login a ser consultado
	 * @return
	 * @throws SQLException
	 */
	public Player findByLogin(String login) throws SQLException{
		this.beforeExecuteQuery();
		this.query = "SELECT id, name, login, score, date_create, game_room"
				+ " FROM player WHERE login = ?;";
		this.queryExec = this.DBConn.prepareStatement(query);
		this.queryExec.setString(1, login);
		ResultSet results = this.queryExec.executeQuery();
		Player p = new Player();
		while (results.next()){
			p.setId(results.getInt(1));
			p.setName(results.getString(2));
			p.setLogin(results.getString(3));
			p.setScore(results.getInt(4));
			p.setDateOfCreate(results.getDate(5));
			p.setGamePlay(this.gameDAO.findById(results.getInt(6)));
		}
		this.afterExecuteQuery();
		return p;
	}
	
	/**
	 * Inserir um player no BD
	 * @param p player
	 * @return
	 * @throws SQLException
	 */
	public int insert(Player p) throws SQLException{
		/*
		 * msg code 0 = OK; 1 = Player ja existe; 2 = Erro no DB 
		 */
		int msgCode = 0;
		try{
			// Consultando se ja existe
			Player player = this.findByLogin(p.getLogin());
			if (player.getLogin() == null){
				this.beforeExecuteQuery();
				this.query = "INSERT INTO player (name, login, password, score, date_create) VALUES (?,?,?,?,?);";
				this.queryExec = this.DBConn.prepareStatement(query);
				this.queryExec.setString(1, p.getName());
				this.queryExec.setString(2, p.getLogin());
				this.queryExec.setString(3, p.getPassword());	
				this.queryExec.setInt(4, 0);
				this.queryExec.setDate(5, p.getDateOfCreate());
				this.queryExec.execute();
				
				this.afterExecuteQuery();
			}else{
				//"Player já existe no sistema";
				msgCode = 1;
			}
			
		}catch (Exception e){
			msgCode = 2;
			System.out.println("Erro ao inserir o registro: :( \n"+e.getMessage());
		}
		return msgCode;
	}
	
	/**
	 * Editar as informacoes de um player
	 * @param p player
	 * @return
	 * @throws SQLException
	 */
	public void update(Player p) throws SQLException{
		try{
			this.beforeExecuteQuery();

			this.query = "UPDATE player SET name=?, login=?, password=?, score=?, date_create=? WHERE id = ?;";
			this.queryExec = this.DBConn.prepareStatement(query);
			this.queryExec.setString(1, p.getName());
			this.queryExec.setString(2, p.getLogin());
			this.queryExec.setString(3, p.getPassword());	
			this.queryExec.setInt(4, p.getScore());
			this.queryExec.setDate(5, p.getDateOfCreate());
			this.queryExec.setInt(6, p.getId());
			this.queryExec.execute();

			this.afterExecuteQuery();
			
		}catch (Exception e){
			System.out.println("Erro ao editar o registro: :( \n"+e.getMessage());
		}
	}
	
	/**
	 * Insere um player na sala
	 * @param p player
	 * @return
	 * @throws SQLException
	 */
	public void updateRoom(Player p) throws SQLException{
		try{
			this.beforeExecuteQuery();

			this.query = "UPDATE player SET game_play=? WHERE id = ?;";
			this.queryExec = this.DBConn.prepareStatement(query);
			this.queryExec.setInt(1, p.getGamePlay().getId());
			this.queryExec.setInt(2, p.getId());
			this.queryExec.execute();

			this.afterExecuteQuery();
			
		}catch (Exception e){
			System.out.println("Erro ao editar o registro: :( \n"+e.getMessage());
		}
	}
	
	/**
	 * Deleta um player
	 * @param p player
	 * @return
	 * @throws SQLException
	 */
	public void delete(Player p) throws SQLException{
		try{
			this.beforeExecuteQuery();

			this.query = "DELETE FROM player WHERE id = ?;";
			this.queryExec = this.DBConn.prepareStatement(query);
			this.queryExec.setInt(1, p.getId());
			this.queryExec.execute();

			this.afterExecuteQuery();
			
		}catch (Exception e){
			System.out.println("Erro ao excluir o registro: :( \n"+e.getMessage());
		}
	}
}
