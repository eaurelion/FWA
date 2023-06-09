package edu.school21.cinema.repositories;

import edu.school21.cinema.models.AuthHistory;
import edu.school21.cinema.models.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    void saveUser(User user) throws SQLException;

    User getUserById(Long id);

    User getUserByLogin(String login);

    void updateUser(User user);

    void addSignInInfo(User user, String address);

    void addSignUpInfo(User user, String address);

    List<AuthHistory> getAuthInfo(String login);
}
