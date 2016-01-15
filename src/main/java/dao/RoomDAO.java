package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Room;
import config.DBConnect;

public class RoomDAO implements Serializable{
	
	private static final long serialVersionUID = -1921887186511514285L;

	private Connection DBConn;
	
	private String query;
	
	private PreparedStatement queryExec;
	
	private PlayerDAO playerDao;
	

	public RoomDAO() {
		super();
		this.DBConn = null;
		this.query = null;
		this.queryExec = null;
		this.playerDao = new PlayerDAO();
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
	public List<Room> getAll() throws SQLException{
		this.beforeExecuteQuery();
		this.query = "SELECT * FROM room ORDER BY date_create;";
		this.queryExec = this.DBConn.prepareStatement(query);
		ResultSet results = this.queryExec.executeQuery();
		List<Room> roomList = new ArrayList<Room>();
		while (results.next()){
			Room r = new Room();
			r.setId(results.getInt(1));
			r.setName(results.getString(2));
			r.setDateOfCreate(results.getDate(3));
			r.setPlayerOwn(this.playerDao.findById(results.getInt(4)));
			r.setNumOfPlayers(results.getInt(5));
			roomList.add(r);
		}
		this.afterExecuteQuery();
		return roomList;
	}
	
	/**
	 * Consulta a sala pelo id.
	 * @return r sala
	 * @throws SQLException
	 */
	public Room findById(Integer id) throws SQLException{
		this.beforeExecuteQuery();
		this.query = "SELECT * FROM room WHERE id = ?;";
		this.queryExec = this.DBConn.prepareStatement(query);
		this.queryExec.setInt(1, id);
		ResultSet results = this.queryExec.executeQuery();
		Room r = new Room();
		while (results.next()){
			r.setId(results.getInt(1));
			r.setName(results.getString(2));
			r.setDateOfCreate(results.getDate(3));
			r.setPlayerOwn(this.playerDao.findById(results.getInt(4)));
			r.setNumOfPlayers(results.getInt(5));
		}
		this.afterExecuteQuery();
		return r;
	}
	
	/**
	 * Consulta a sala pelo nome
	 * @return r sala
	 * @throws SQLException
	 */
	public Room findByName(String name) throws SQLException{
		this.beforeExecuteQuery();
		this.query = "SELECT * FROM room WHERE name = ?;";
		this.queryExec = this.DBConn.prepareStatement(query);
		this.queryExec.setString(1, name);
		ResultSet results = this.queryExec.executeQuery();
		Room r = new Room();
		while (results.next()){
			r.setId(results.getInt(1));
			r.setName(results.getString(2));
			r.setDateOfCreate(results.getDate(3));
			r.setPlayerOwn(this.playerDao.findById(results.getInt(4)));
			r.setNumOfPlayers(results.getInt(5));
		}
		this.afterExecuteQuery();
		return r;
	}
	
	/**
	 * Inserir uma sala no BD
	 * @param r sala
	 * @return msgCode 
	 * @throws SQLException
	 */
	public int insert(Room r) throws SQLException{
		/*
		 * msg code 0 = OK; 1 = Sala ja existe; 2 = Erro no DB 
		 */
		int msgCode = 0;
		try{
			// Consultando se ja existe
			Room room = this.findByName(r.getName());
			if (room == null){
				this.beforeExecuteQuery();
				
				this.query = "INSERT INTO room (name, date_create, player_own, num_players) VALUES (?,?,?,?,?,?);";
				this.queryExec = this.DBConn.prepareStatement(query);
				this.queryExec.setString(1, r.getName());
				this.queryExec.setDate(2, r.getDateOfCreate());
				this.queryExec.setInt(3, r.getPlayerOwn().getId());	
				this.queryExec.setInt(4, r.getNumOfPlayers());
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
	 * @param r sala
	 * @throws SQLException
	 */
	public void delete(Room r) throws SQLException{
		try{
			this.beforeExecuteQuery();

			this.query = "DELETE FROM room WHERE id = ?;";
			this.queryExec = this.DBConn.prepareStatement(query);
			this.queryExec.setInt(1, r.getId());
			this.queryExec.execute();

			this.afterExecuteQuery();
			
		}catch (Exception e){
			System.out.println("Erro ao excluir o registro: :( \n"+e.getMessage());
		}
	}

}
