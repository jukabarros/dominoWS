package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
	
	public static Properties getProp() throws IOException {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream("./gameConfig.properties");
        props.load(file);
        return props;
 
    }   

	public static void  main(String args[]) throws IOException {
        
        Properties prop = getProp();
        
        String host = prop.getProperty("dataBase.host");
		String password = prop.getProperty("dataBase.password");
		String database = prop.getProperty("dataBase.name");
         
        System.out.println("Login = " + database);
        System.out.println("Host = " + host);
        System.out.println("Password = " + password);
    }
}
