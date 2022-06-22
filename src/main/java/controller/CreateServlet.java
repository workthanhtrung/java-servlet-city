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
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@WebServlet(name = "CreateServlet", value = "/create")
public class CreateServlet extends HttpServlet {
    private CityDAO cityDAO;
    private NationDAO nationDAO;

    @Override
    public void init() {
        cityDAO = new CityDAO();
        nationDAO = new NationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Nation> nationList = nationDAO.selectAllNations();
        req.setAttribute("nations", nationList);
        req.setAttribute("city", new City());
        req.setAttribute("filled", false);
        RequestDispatcher dispatcher = req.getRequestDispatcher("city/create.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String nation = req.getParameter("nation");
        Float area = Float.valueOf(req.getParameter("area"));
        Float population = Float.valueOf(req.getParameter("population"));
        Float gdp = Float.valueOf(req.getParameter("gdp"));
        String description = req.getParameter("description");

        City newCity = new City(name, nation, area, population, gdp, description);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<City>> constraintViolations = validator.validate(newCity);
        if (!constraintViolations.isEmpty()) {
            String errors = "<ul>";
            for (ConstraintViolation<City> constraintViolation : constraintViolations) {
                errors += "<li>" + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage()
                        + "</li>";
            }
            errors += "</ul>";
            req.setAttribute("errors", errors);
            List<Nation> nations = nationDAO.selectAllNations();
            req.setAttribute("nations", nations);
            req.setAttribute("city", newCity);
            req.setAttribute("filled", true);
            RequestDispatcher dispatcher = req.getRequestDispatcher("city/create.jsp");
            dispatcher.forward(req, resp);
        } else {
            try {
                cityDAO.insertCity(newCity);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resp.sendRedirect("/home");
        }
    }
}

