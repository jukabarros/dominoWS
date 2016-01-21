package service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import model.Game;
import model.Player;
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
	
	public Game getByName(String name){
		try{
			return this.dao.getByName(name);
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public int insert(Game g) {
		try {
			return this.dao.insert(g);
		} catch (SQLException e) {
			e.printStackTrace();
			return 2;
		}
	}
	
	
	public void delete(Game g){
		try {
			this.dao.delete(g);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public int getNumOfPlayersGame(Game g) {
		try {
			return this.dao.getNumOfPlayers(g);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public void updateNumOfPlayers(Game g, String op) {
		this.dao.updatePlayersGame(g, op);
	}
	
	public void insertPlayerinGame(Player p, Game g){
		try {
			this.dao.insertPlayerinGame(p, g);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
