package controller;

import dao.CityDAO;
import dao.NationDAO;
import model.City;
import model.Nation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "EditServlet", value = "/edit")
public class EditServlet extends HttpServlet {
    private CityDAO cityDAO;
    private NationDAO nationDAO;

    @Override
    public void init() {
        cityDAO = new CityDAO();
        nationDAO = new NationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        City existingcity = cityDAO.selectCity(id);
        List<Nation> nationList = nationDAO.selectAllNations();

        RequestDispatcher dispatcher = request.getRequestDispatcher("city/edit.jsp");

        request.setAttribute("city", existingcity);
        request.setAttribute("nations", nationList);

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String nation = request.getParameter("nation");
        Float area = Float.parseFloat(request.getParameter("area"));
        Float population = Float.parseFloat(request.getParameter("population"));
        Float gdp = Float.parseFloat(request.getParameter("gdp"));
        String description = request.getParameter("description");

        City editedCity = new City(id, name, nation, area, population, gdp, description);
        try {
            cityDAO.updateCity(editedCity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/home");
    }
}
