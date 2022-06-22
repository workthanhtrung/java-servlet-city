package dao;

import model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAO implements ICityDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/city_information?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    private static final String INSERT_CITY_SQL = "insert into city"
            + " (name, nation, area, population, gdp, description)"
            + " values (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_CITIES = "select * from city";
    private static final String SELECT_CITY_BY_ID = "select * from city where id = ?";
    private static final String DELETE_CITIES_SQL = "delete from city where id = ?";
    private static final String UPDATE_CITY_SQL = "update city set name = ?, " +
            "nation = ?, area = ?, population = ?, GDP = ?, description = ? where id = ?;";
    private static final String FIND_BY_NATION =
            "select city.* from city where city.nation like ?";
//            "select city.* " +
//            "from city " +
//            "WHERE city.nation in ( " +
//            "select nation2.name " +
//            "from nation2 " +
//            "WHERE nation2.name LIKE ?);";

    private static final String SORT_BY_NAME = "select * from city order by name";
    private static final String SELECT_ALL_CITIES_PER_PAGE = "SELECT * FROM city LIMIT ?, ?;";
    private static final String SELECT_ALL_CITIES_PER_PAGE_AFTER_SORT = "SELECT * FROM city ORDER BY name LIMIT ?, ?";
    private static final String GET_NUMBER_OF_ROWS = "SELECT COUNT(id) AS num FROM city;";

    public CityDAO() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public City selectCity(int id) {
        City city = null;
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_CITY_BY_ID);
        ) {
            stmt.setInt(1, id);
            System.out.println(stmt);

            ResultSet rs = stmt.executeQuery();

            //cach 1: create variables then fill to contractor
            while (rs.next()) {
                String name = rs.getString("name");
                String nation = rs.getString("nation");
                Float area = rs.getFloat("area");
                Float population = rs.getFloat("population");
                Float gdp = rs.getFloat("gdp");
                String description = rs.getString("description");

                city = new City(id, name, nation, area, population, gdp, description);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return city;
    }

    @Override
    public List<City> selectAllCities() {
        List<City> cityList = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ALL_CITIES);
        ) {
            System.out.println(stmt);
            ResultSet rs = stmt.executeQuery();

            //cach 2: use contractor with setters inside
            while (rs.next()) {
                City newCity = new City();
                newCity.setId(rs.getInt("id"));
                newCity.setName(rs.getString("name"));
                newCity.setNation(rs.getString("nation"));
                newCity.setArea(rs.getFloat("area"));
                newCity.setPopulation(rs.getFloat("population"));
                newCity.setGdp(rs.getFloat("gdp"));
                newCity.setDescription(rs.getString("description"));

                cityList.add(newCity);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return cityList;
    }

    @Override
    public void insertCity(City city) throws SQLException {
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_CITY_SQL);
        ) {
            stmt.setString(1, city.getName());
            stmt.setString(2, city.getNation());
            stmt.setFloat(3, city.getArea());
            stmt.setFloat(4, city.getPopulation());
            stmt.setFloat(5, city.getGdp());
            stmt.setString(6, city.getDescription());

            System.out.println(stmt);

            stmt.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public boolean updateCity(City city) throws SQLException {
        boolean rowUpdated;
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_CITY_SQL);
        ) {
            stmt.setString(1, city.getName());
            stmt.setString(2, city.getNation());
            stmt.setDouble(3, city.getArea());
            stmt.setDouble(4, city.getPopulation());
            stmt.setDouble(5, city.getGdp());
            stmt.setString(6, city.getDescription());

            stmt.setInt(7, city.getId());
            System.out.println(stmt);

            rowUpdated = stmt.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    @Override
    public boolean deleteCity(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CITIES_SQL);
        ) {
            statement.setInt(1, id);
            System.out.println(statement);

            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public List<City> findByNation(String searchString) {
        List<City> foundCitiesList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NATION);
        ) {
            searchString = '%' + searchString + '%';
            statement.setString(1, searchString);
            System.out.println(statement);

            ResultSet rs = statement.executeQuery();

            // cach 3: use contractor with direct values, without creating variables or using setter
            while (rs.next()) {
                City foundCity = new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("nation"),
                        rs.getFloat("area"),
                        rs.getFloat("population"),
                        rs.getFloat("gdp"),
                        rs.getString("description")
                );
                foundCitiesList.add(foundCity);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return foundCitiesList;
    }

    @Override
    public List<City> sortByName() {
        List<City> listSorted = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(SORT_BY_NAME);
        ) {
            System.out.println(stmt);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                City city = new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("nation"),
                        rs.getFloat("area"),
                        rs.getFloat("population"),
                        rs.getFloat("gdp"),
                        rs.getString("description")
                );
                listSorted.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listSorted;
    }

    @Override
    public List<City> findCitiesPerPage(int currentPage, int recordsPerPage, boolean sort) {
        List<City> cities = new ArrayList<>();
        int start = currentPage * recordsPerPage - recordsPerPage;

        try (Connection con = getConnection();
        ) {
            PreparedStatement stmt = null;
            if (sort) {
                stmt = con.prepareStatement(SELECT_ALL_CITIES_PER_PAGE_AFTER_SORT);
            } else {
                stmt = con.prepareStatement(SELECT_ALL_CITIES_PER_PAGE);
            }
            stmt.setInt(1, start);
            stmt.setInt(2, recordsPerPage);

            System.out.println(stmt);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                City city = new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("nation"),
                        rs.getFloat("area"),
                        rs.getFloat("population"),
                        rs.getFloat("gdp"),
                        rs.getString("description")
                );
                cities.add(city);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cities;
    }

    @Override
    public int getNumberOfRows() {
        int numOfRows = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_NUMBER_OF_ROWS);) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                numOfRows = rs.getInt("num");
            }
        } catch (SQLException ex) {
            printSQLException(ex);
        }
        return numOfRows;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
