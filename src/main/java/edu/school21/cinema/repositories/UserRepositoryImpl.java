package edu.school21.cinema.repositories;

import edu.school21.cinema.models.AuthHistory;
import edu.school21.cinema.models.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user;

            try {
                user = new User(resultSet.getLong("id"), resultSet.getString("login"),
                        resultSet.getString("password"), resultSet.getString("firstname"),
                        resultSet.getString("lastname"), resultSet.getString("phone"));
            } catch (EmptyResultDataAccessException e) {
                System.err.println("Wrong user's attributes.\n" + e.getMessage());
                return null;
            }
            return user;
        }
    }

    private static class AuthHistoryMapper implements RowMapper<AuthHistory> {
        @Override
        public AuthHistory mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            AuthHistory authHistory = new AuthHistory();

            try {
                authHistory.setUser_id(resultSet.getLong("user_id"));
                authHistory.setTime(resultSet.getString("time"));
                authHistory.setType(resultSet.getString("type"));
                authHistory.setAddress(resultSet.getString("address"));
            } catch (EmptyResultDataAccessException e) {
                System.err.println("Error in logging time data.\n" + e.getMessage());
                return null;
            }
            return authHistory;
        }
    }

    @Override
    public User getUserById(Long id) {
        try {
            String query = "select * from users where id=?";

            return jdbcTemplate.queryForObject(query, new UserMapper(), new Object[]{id});
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Data access failed.\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public User getUserByLogin(String login) {
        try {
            String query = "select * from users where login=?";

            return jdbcTemplate.queryForObject(query, new UserMapper(), new Object[]{login});
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Data access failed.\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public void saveUser(User user) {
        try {
            String query = "insert into users(login, password, firstname, lastname, phone) values (?, ?, ?, ?, ?)";

            jdbcTemplate.execute(query, new PreparedStatementCallback<Object>() {
                @Override
                public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                    preparedStatement.setString(1, user.getLogin());
                    preparedStatement.setString(2, user.getPassword());
                    preparedStatement.setString(3, user.getFirstName());
                    preparedStatement.setString(4, user.getLastName());
                    preparedStatement.setString(5, user.getPhoneNumber());
                    return preparedStatement.execute();
                }
            });
        } catch (DuplicateKeyException e) {
            System.out.println("Error during registration.\n" + e.getMessage());
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            String query = "update users set firstname=?, lastname=?, phone=? where id=?";
            jdbcTemplate.execute(query, new PreparedStatementCallback<Object>() {
                @Override
                public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                    preparedStatement.setString(1, user.getFirstName());
                    preparedStatement.setString(2, user.getLastName());
                    preparedStatement.setString(3, user.getPhoneNumber());
                    preparedStatement.setLong(4, user.getId());
                    return preparedStatement.execute();
                }
            });
        } catch (DuplicateKeyException e) {
            System.out.println("Error during updating.\n" + e.getMessage());
        }
    }

    @Override
    public void addSignInInfo(User user, String address) {
        String query = "insert into auth(user_id, type, address, time) values (?, ?, ?, ?)";

        jdbcTemplate.execute(query, new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setString(2, "sign_in");
                preparedStatement.setString(3, address);
                preparedStatement.setString(4, dateFormat.format(new Date()));
                return preparedStatement.execute();
            }
        });
    }

    @Override
    public void addSignUpInfo(User user, String address) {
        String query = "insert into auth(user_id, type, address, time) values (?, ?, ?, ?)";

        jdbcTemplate.execute(query, new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setString(2, "sign_up");
                preparedStatement.setString(3, address);
                preparedStatement.setString(4, dateFormat.format(new Date()));
                return preparedStatement.execute();
            }
        });
    }

    @Override
    public List getAuthInfo(String login) {
        String query = "select * from auth where user_id=" + getUserByLogin(login).getId();

        return jdbcTemplate.query(query, new AuthHistoryMapper());
    }
}
