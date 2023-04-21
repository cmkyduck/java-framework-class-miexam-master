package kr.ac.jejunu.user;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

public class UserDaoTests {
    @Test
    public void getforJeju() throws SQLException, ClassNotFoundException {
        Long id = 1l;
        String name = "hulk";
        String password = "1234";
        //UserDao userDao = new JejuUserDao();
        //ConnectionMaker connectionMaker = new JejuConnectionMaker();
        //UserDao userDao = new UserDao(connectionMaker);
        DaoFactory daoFactory = new DaoFactory();
        UserDao userDao = daoFactory.getUserDao();
        User user = userDao.findById(id);
        assertThat(user.getId(), is(id));
        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }

    @Test
    public void insertforJeju() throws SQLException, ClassNotFoundException {
        String name = "duck";
        String password = "8923";
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        //UserDao userDao = new JejuUserDao();
        //ConnectionMaker connectionMaker = new JejuConnectionMaker();
        //UserDao userDao = new UserDao(connectionMaker);
        DaoFactory daoFactory = new DaoFactory();
        UserDao userDao = daoFactory.getUserDao();
        userDao.insert(user);
        assertThat(user.getId(), greaterThan(1l));

        User insertedUser = userDao.findById(user.getId());
        assertThat(insertedUser.getId(), is(user.getId()));
        assertThat(insertedUser.getName(), is(name));
        assertThat(insertedUser.getPassword(), is(password));
    }
}
