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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    private CityDAO cityDAO;

    @Override
    public void init() {
        cityDAO = new CityDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int recordsPerPage = 5;
        int currentPage = 1;
        String page = request.getParameter("page");
        if (page == null) {

        } else {
            currentPage = Integer.parseInt(page);
        }

        boolean sort = false;
        if (request.getParameter("sort") == null) {

        } else {
            sort = Boolean.parseBoolean(request.getParameter("sort"));
        }
        List<City> cities = cityDAO.findCitiesPerPage(currentPage, recordsPerPage, sort);

        int rows = cityDAO.getNumberOfRows();
        int nOfPages = rows / recordsPerPage;

        if (rows % recordsPerPage > 0) {
            nOfPages++;
        }

        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);

        request.setAttribute("allCities", cities);
        request.setAttribute("sort", sort);

        RequestDispatcher dispatcher = request.getRequestDispatcher("city/list.jsp");
        dispatcher.forward(request, response);

//        List<City> cities = cityDAO.selectAllCities();
//        request.setAttribute("allCities", cities);
//        request.getRequestDispatcher("city/list.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
