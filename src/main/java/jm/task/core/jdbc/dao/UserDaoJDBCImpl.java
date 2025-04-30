package jm.task.core.jdbc.dao;

import com.mysql.cj.protocol.Resultset;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    Connection connection = Util.getConnection();

    public void createUsersTable() {
        final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users (\n" +
                "  id INT NOT NULL AUTO_INCREMENT,\n" +
                "  name varchar(45) NOT NULL,\n" +
                "  lastName varchar(45) NOT NULL,\n" +
                "  age TINYINT NOT NULL,\n" +
                "  PRIMARY KEY (id),\n" +
                "  UNIQUE KEY id_UNIQUE (id)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb3";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(CREATE_USERS_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS users";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(DROP_USERS_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String SAVE_USER = "INSERT INTO users ( name, lastName, age) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(SAVE_USER))
        {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user", e);
        }
    }

    public void removeUserById(long id) {
        final String REMOVE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(REMOVE_USER_BY_ID)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error removing user by id", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        final String GET_ALL_USERS = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(GET_ALL_USERS)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
        }
        catch (SQLException e) {
            System.err.println("getAllUsersUnWork" + e.getMessage());
            e.printStackTrace();
        }
        return users;

    }

    public void cleanUsersTable() {
        final String CLEAN_USERS_TABLE = "TRUNCATE TABLE users";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(CLEAN_USERS_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException("Error cleaning users table", e);
        }
    }
}
