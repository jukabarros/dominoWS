package service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import model.Game;
import dao.GameDAO;

public class GameService implements Serializable{

	private static final long serialVersionUID = -9142826826967122880L;
	
	private GameDAO dao;

	public GameService() {
		super();
		this.dao = new GameDAO();
	}
	
	public List<Game> getAll(){
		try{
			return this.dao.getAll();
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Game> findByName(String name){
		try{
			return this.dao.findByName(name);
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public int insert(Game r) {
		try {
			return this.dao.insert(r);
		} catch (SQLException e) {
			e.printStackTrace();
			return 2;
		}
	}
	
	
	public void delete(Game r){
		try {
			this.dao.delete(r);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public int getNumOfPlayersGame(Game r) {
		try {
			return this.dao.getNumOfPlayers(r);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public void updateNumOfPlayers(Game r, String op) {
		this.dao.updatePlayersGame(r, op);
	}

}
