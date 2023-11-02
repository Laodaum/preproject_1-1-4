package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/1_1_3-4_jdbc";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private Connection connection;

    public Connection getConnection() {
      try {
          Driver driver = new com.mysql.cj.jdbc.Driver();
          DriverManager.registerDriver(driver);
          connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
          if (!connection.isClosed()) {
              System.out.println("соединение установлено");
          } else {
              System.out.println("не успех");
          }
      } catch (SQLException e) {
          System.err.println("не удалось загрузить класс драйвера");
      }
        return connection;
    }
}
