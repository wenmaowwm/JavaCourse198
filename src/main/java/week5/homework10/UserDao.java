package week5.homework10;

import week5.homework10.Dto.User;

import java.sql.SQLException;

public interface UserDao {
    int addUser(User user);
    int updateUser(User user);
    User queryUser(long id) throws SQLException;
    int deleteUser(long id);
}
