package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {

	private Connection conn;

	public Connection getDBConnection(){
		try{
//			Properties prop = ReadProperties.getProp();
			String host = "127.0.0.1"; //prop.getProperty("dataBase.host");
			String user = "root"; //prop.getProperty("dataBase.user");;
			String password = "bsi2011"; //prop.getProperty("dataBase.password");
			String database = "dominoWS"; //prop.getProperty("dataBase.name");

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+database, user, password);
			return conn;
		}catch (Exception e){
			System.out.println("Erro ao se conectar com o BD: "+e.getMessage());
		}
		return conn;
	}

}
