package com.emlakjet.task.mi.dao.impl;

import com.emlakjet.task.mi.dao.Dao;
import com.emlakjet.task.mi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@DependsOn("dataSource")
public class UserDao implements Dao<User> {

    @Autowired
    DataSource dataSource;

    public UserDao( ) {
    }

    @Override
    public User get(long id) {
        User result = null;
        String sql = "SELECT * FROM emlakjetdb.users where id = ?";
        try {
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(sql);

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            long userid = resultSet.getLong("ID");
            String name = resultSet.getString("NAME");
            String surname = resultSet.getString("SURNAME");
            String email = resultSet.getString("EMAIL");
            result = new User(userid, name, surname, email);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }

    public long getUserId(String name, String surname, String email) {
        long result = 0;
        String sql = "SELECT * FROM emlakjetdb.users where name = ? and surname = ? and email = ?";
        try {
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            result = resultSet.getLong("ID");
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }

    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        String sql = "SELECT * FROM emlakjetdb.users";
        try {
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long userid = resultSet.getLong("ID");
                String name = resultSet.getString("NAME");
                String surname = resultSet.getString("SURNAME");
                String email = resultSet.getString("EMAIL");
                result.add(new User(userid, name, surname, email));
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return result;
    }

    public User save(User newUser) {
        String sql = "INSERT INTO emlakjetdb.users (name, surname, email) VALUES(?, ?, ?)";
        try {
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, newUser.getName());
            preparedStatement.setString(2, newUser.getSurname());
            preparedStatement.setString(3, newUser.getEmail());

            int rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
        return newUser;
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE  emlakjetdb.users where id = ?";
        try {
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }
}
