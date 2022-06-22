package dao;

import model.Nation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NationDAO implements INationDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/city_information?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    private static final String SELECT_ALL_NATIONS = "select * from nation2";

    public NationDAO() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public List<Nation> selectAllNations() {
        List<Nation> nationList = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL_NATIONS);
        ) {
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                nationList.add(new Nation(id, name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nationList;
    }
}
