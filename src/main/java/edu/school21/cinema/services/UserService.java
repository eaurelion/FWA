package edu.school21.cinema.services;

import edu.school21.cinema.models.AuthHistory;
import edu.school21.cinema.models.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    boolean signUp(String login, String password, String address) throws SQLException;

    boolean signIn(String login, String password, String address);

    User getProfile(String login);

    void updateProfile(User user);

    List<AuthHistory> getAuth(String login);
}
