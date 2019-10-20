package sample.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {
    public static Connection pool[];
    public static final int MAX_CONNECTIONS = 10;
    private String DB = "vilaUrbana";

    static {
        pool = new Connection[MAX_CONNECTIONS];
    }

    public static Connection getConnection() throws SQLException
    {
        for(int i = 0; i < pool.length; i++) {
            if(pool[i] == null || pool[i].isClosed()){
                pool[i] = DriverManager.getConnection("jdbc:sqlite:vilaUrbana");
                return pool[i];
            }
        }

        throw new SQLException("Muitas conexões abertas!");
    }
}
