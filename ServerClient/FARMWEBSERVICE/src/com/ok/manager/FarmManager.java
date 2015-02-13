package com.ok.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.microsoft.sqlserver.jdbc.SQLServerStatement;

public class FarmManager {
	public enum DatabaseType {
		MySQL, MicrosoftSQL
	}

	private DatabaseType databaseType = null;

	private String server = null;
	
	private String pointConnection = null;

	private String database = null;

	private String user = null;

	private String password = null;

	private static Connection connection = null;

	private String connectionString = null;


	public static void main(String[] args) {

		try {

			ResultSet resultSet = null;
			FarmManager farmManager = null;
			int  Typess[];
		    String  Columns[];
			farmManager = new FarmManager();
			farmManager.setDatabaseType(DatabaseType.MicrosoftSQL);
			farmManager.setServer("localhost");
			farmManager.setPointConnection("1433");
			farmManager.setDatabase("FIELD");
			farmManager.setUser("field_application");
			farmManager.setPassword("parola");
			resultSet = 
            farmManager.query(" SELECT F.FieldName,I.humidity,I.temperature FROM FIELD AS F "
                             +" LEFT OUTER JOIN INFORMATION AS I "
		                     +" ON F.InformationID= I.InformationID "
                             +" WHERE I.InformationID IN " 
                                  +" (SELECT F.InformationID FROM FIELD AS F WHERE F.FieldID IN " 
                                  +" (SELECT L.FieldID FROM LOGINFIELD AS L  WHERE L.UserName='x' )); ");

			while (resultSet.next()) {

				System.out.println(resultSet.getString("FieldName"));
				System.out.println(resultSet.getInt("humidity"));
				System.out.println(resultSet.getInt("temperature"));

			}
			 ResultSetMetaData rsMetaData = resultSet.getMetaData();

			    int numberOfColumns = rsMetaData.getColumnCount();
			    System.out.println("resultSet MetaData column Count=" + numberOfColumns);
			    Typess=new int[numberOfColumns];
			    Columns=new String[numberOfColumns];
			    for (int i = 1; i <= numberOfColumns; i++) {
			    	
			    	//arabaListesi.add(rsMetaData.getColumnName(i));
			    	Typess[i-1]= rsMetaData.getColumnType(i);
			    	Columns[i-1]=rsMetaData.getColumnName(i);
			     
			    }
			  
			   // resultSet.beforeFirst();
			    while (resultSet.next()) {
			    	
			    for (int i = 1; i <= numberOfColumns; i++) 
			    { 
			    	 if (Typess[i-1] == Types.VARCHAR || Typess[i-1] == Types.CHAR) 
			    		 System.out.println(resultSet.getString(Columns[i-1]));
			    	 else 
			    		 System.out.println(resultSet.getInt(Columns[i-1]));
			            
			    }
			    }

		} catch (Exception e) {

			e.printStackTrace();

		}
		finally {
			try {
				EndConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	
	public ResultSet query(String sqlKomutu) throws SQLException {
		
		ResultSet resultSet = null;
		
		resultSet = this.getStatement().executeQuery(sqlKomutu);

		return resultSet;
		
	}

	
	public Boolean execute(String sqlKomutu) throws SQLException {
		
		return this.getStatement().execute(sqlKomutu);

	}
	

	public Statement getStatement() throws SQLException {

		Statement statement = null;

		statement = this.getBaglanti().createStatement();
		
		return statement;

	}


	private Connection getBaglanti() throws SQLException {

		if (connection == null) {

			connection = DriverManager.getConnection(this.getBaglantiDizgisi());

		}

		return connection;

	}
	public static void EndConnection() throws SQLException {

			if (connection != null) connection.close();;

	}


	private String getBaglantiDizgisi() throws SQLException {

		if (connectionString == null) {

			switch (this.getDatabaseType()) {
			case MySQL:

				connectionString = "jdbc:mysql://" + this.getServer() + ":" + this.getPointConnection() + "/" + this.getDatabase() + "?user=" + this.getUser() + "&password=" + this.getPassword() + "";
				break;

			case MicrosoftSQL:

				connectionString = "jdbc:sqlserver://" + this.getServer() + ":" + this.getPointConnection() + ";databasename=" + this.getDatabase() + ";user=" + this.getUser() + ";password=" + this.getPassword() + "";
				break;

			default:

				throw new SQLException("Bu veritabani türü icin baglanti dizgisi olusuturulamiyor.");

			}

		}

		return connectionString;

	}


	public DatabaseType getDatabaseType() {

		if (databaseType == null) {

			databaseType = DatabaseType.MySQL;

		}

		return databaseType;

	}


	public void setDatabaseType(DatabaseType databaseType) {

		this.databaseType = databaseType;

	}


	public String getServer() {

		if (server == null) {

			server = "";

		}

		return server;

	}


	public void setServer(String server) {

		this.server = server;

	}


	public String getPointConnection() {

		if (pointConnection == null) {

			pointConnection = "";

		}

		return pointConnection;

	}


	public void setPointConnection(String pointConnection) {

		this.pointConnection = pointConnection;

	}


	public String getDatabase() {

		if (database == null) {

			database = "";

		}

		return database;

	}


	public void setDatabase(String database) {

		this.database = database;

	}


	public String getUser() {

		if (user == null) {

			user = "";

		}

		return user;

	}


	public void setUser(String user) {

		this.user = user;

	}


	public String getPassword() {

		if (password == null) {

			password = "";

		}

		return password;

	}


	public void setPassword(String password) {

		this.password = password;

	}

}
