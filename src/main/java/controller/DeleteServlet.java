package controller;

import dao.CityDAO;
import model.City;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "DeleteServlet", value = "/delete")
public class DeleteServlet extends HttpServlet {
    private CityDAO cityDAO;

    @Override
    public void init() throws ServletException {
        cityDAO = new CityDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        City city = cityDAO.selectCity(id);
        req.setAttribute("city", city);
        RequestDispatcher dispatcher = req.getRequestDispatcher("city/delete.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            cityDAO.deleteCity(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/home");
    }
}
