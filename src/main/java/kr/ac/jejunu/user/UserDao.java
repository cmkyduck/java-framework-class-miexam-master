package kr.ac.jejunu.user;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private final DataSource dataSource;
    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User findById(Long id) throws ClassNotFoundException, SQLException {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/jeju", "root", "0000");
        //Connection connection = getConnection();
        StatementStrategy statementStrategy = new FindStatementStrategy(id);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = dataSource.getConnection();
            //preparedStatement = connection.prepareStatement("select id, name, password from userinfo where id = ?");
            //preparedStatement.setLong(1, id);
            preparedStatement = statementStrategy.makeStatement(connection);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    public void insert(User user) throws SQLException, ClassNotFoundException {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/jeju", "root", "0000");
        //Connection connection = getConnection();
        StatementStrategy statementStrategy = new InsertStatementStrategy(user);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            //preparedStatement = connection.prepareStatement
            //        ("insert into userinfo (name, password) values ( ?, ? )"
            //                , Statement.RETURN_GENERATED_KEYS);
            //preparedStatement.setString(1, user.getName());
            //preparedStatement.setString(2, user.getPassword());
            preparedStatement = statementStrategy.makeStatement(connection);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getLong(1));
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(User user) throws SQLException {
        StatementStrategy statementStrategy = new UpdateStatementStrategy(user);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            //preparedStatement = connection.prepareStatement
            //        ("update userinfo set name = ?, password = ? where id = ?");
            //preparedStatement.setString(1, user.getName());
            //preparedStatement.setString(2, user.getPassword());
            //preparedStatement.setLong(3, user.getId());
            preparedStatement = statementStrategy.makeStatement(connection);
            preparedStatement.executeUpdate();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void delete(Long id) throws SQLException {
        StatementStrategy statementStrategy = new DeleteStatementStrategy(id);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            //preparedStatement = connection.prepareStatement
            //        ("delete from userinfo where id = ?");
            //preparedStatement.setLong(1, id);
            preparedStatement = statementStrategy.makeStatement(connection);
            preparedStatement.executeUpdate();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //abstract public Connection getConnection() throws ClassNotFoundException, SQLException;
}
