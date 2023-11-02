package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = new Util().getConnection();


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id int NOT NULL PRIMARY KEY AUTO_INCREMENT, name varchar(45), lastName varchar(45), age int)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name,lastName,age) VALUES (?,?,?)");
            preparedStatement.setString( 1, name );
            preparedStatement.setString( 2, lastName );
            preparedStatement.setInt( 3, age );

            preparedStatement.execute();

            System.out.println( "User с именем – " + name + " добавлен в базу данных" );
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException excep) {
                    System.err.print("Error while transaction is being rolled back");
                }
            }
        }
    }

    public void removeUserById(long id) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id=?");
            preparedStatement.setInt( 1, (int) id);

            preparedStatement.executeUpdate();

            System.out.println("User с id – "+id+ " удален");
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException excep) {
                    System.err.print("Error while transaction is being rolled back");
                }
            }
        }
    }

    public List<User> getAllUsers() {
        List<User>  listUsers = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                listUsers.add(new User(
                    resultSet.getLong("id"),
                    resultSet.getString( "name"),
                    resultSet.getString( "lastName"),
                    resultSet.getByte( "age")
                    ));
        }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException excep) {
                    System.err.print("Error while transaction is being rolled back");
                }
            }
        }
        return listUsers;
    }

    public void cleanUsersTable() {
        try {
            connection.createStatement().executeUpdate("DELETE FROM users");
            System.out.println("База очищена");
        } catch (SQLException e) {
            System.out.println("База НЕ очищена");
        }
    }
    }

