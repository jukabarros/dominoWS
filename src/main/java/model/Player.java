package model;

import java.io.Serializable;
import java.sql.Date;

public class Player implements Serializable{
	
	private static final long serialVersionUID = 5489053282281886549L;

	private Integer id;
	
	private String name;
	
	private String login;
	
	private String password;
	
	private Integer score;
	
	private Date dateOfCreate;
	
	private Room gameRoom;

	public Player() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getDateOfCreate() {
		return dateOfCreate;
	}

	public void setDateOfCreate(Date dateOfCreate) {
		this.dateOfCreate = dateOfCreate;
	}

	public Room getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(Room gameRoom) {
		this.gameRoom = gameRoom;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", login=" + login + "]";
	}

}
