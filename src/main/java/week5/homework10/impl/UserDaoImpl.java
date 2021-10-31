package week5.homework10.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import week5.homework10.Dto.User;
import week5.homework10.UserDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addUser(User user) {
        String sql = "insert into user (id, name) values(?, ?)";
        //return jdbcTemplate.update(sql, user.getId(), user.getUsername());
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, user.getId());
                ps.setString(2, user.getUsername());
            }
        });
    }

    @Override
    public int updateUser(User user) {
        String sql = "update user set name= ? where id = ?";
        //return jdbcTemplate.update(sql, user.getUsername(), user.getId());
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, user.getUsername());
                ps.setLong(2, user.getId());
            }
        });
    }

    @Override
    public User queryUser(long id) throws SQLException {
        String sql = "select name from user where id = ?";
        List<User> retList = jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, id);
            }
        }, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User ret = new User();
                ret.setUsername(resultSet.getString(1));
                ret.setId(id);
                return ret;
            }
        });
        return retList.get(0);
//        return jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
//            @Override
//            public User mapRow(ResultSet resultSet, int i) throws SQLException {
//                User ret = new User();
//                ret.setUsername(resultSet.getString(1));
//                ret.setId(id);
//                return ret;
//            }
//        }, id);
    }

    @Override
    public int deleteUser(long id) {
        String sql = "delete from user where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
