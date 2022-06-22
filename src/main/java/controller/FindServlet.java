package controller;

import dao.CityDAO;
import dao.NationDAO;
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

@WebServlet(name = "FindServlet", value = "/find")
public class FindServlet extends HttpServlet {
    private CityDAO cityDAO;

    private final int RECORDS_PER_PAGE = 5;

    @Override
    public void init() {
        cityDAO = new CityDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int currentPage = 1;
        String page = request.getParameter("page"); // pagination
        // xem lai ở đây vì khi ấn số trang, nó sẽ gửi số page =? và search=?
        // cái doget này mới là cái sẽ trả về bản ghi theo limit và offset chứ ko phải doPost,
        // doPost ở dưới chỉ trả về trang đầu tiên thôi

        if (page == null) {

        } else {
            currentPage = Integer.parseInt(page);
        }

        String searchString = request.getParameter("search_by_country");
        List<City> cities = cityDAO.findByNation(searchString);

        List<City> citiesPerPage = getCitiesPerPage(cities, currentPage, RECORDS_PER_PAGE);

        int nOfPages = getNumOfPages(cities.size());

        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", RECORDS_PER_PAGE);

        request.setAttribute("cities", citiesPerPage);
        request.setAttribute("inputString", searchString);

        RequestDispatcher dispatcher = request.getRequestDispatcher("city/search.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchString = request.getParameter("search_by_country");
        List<City> listFoundCities = cityDAO.findByNation(searchString);
        request.setAttribute("cities", listFoundCities);
        request.setAttribute("search_string", searchString);

        int currentPage = 1;

        List<City> citiesFirstPage = getCitiesPerPage(listFoundCities, 1, RECORDS_PER_PAGE);

        int totalPages = getNumOfPages(listFoundCities.size());

        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", RECORDS_PER_PAGE);

        request.setAttribute("citiesFirstPage", citiesFirstPage);
        request.setAttribute("inputString", searchString);

        RequestDispatcher dispatcher = request.getRequestDispatcher("city/search.jsp");
        dispatcher.forward(request, response);
    }

    private int getNumOfPages(int rows) {
        int totalPages = rows / RECORDS_PER_PAGE;

        if (rows % RECORDS_PER_PAGE > 0) {
            totalPages++;
        }
        return totalPages;
    }

    private List<City> getCitiesPerPage(List<City> cities, int currentPage, int recordsPerPage) {
        List<City> result = new ArrayList<City>();
        int begin = (currentPage - 1) * recordsPerPage;
        int end = 0;
        if (recordsPerPage > (cities.size() - begin)) {
            end = cities.size();
        } else {
            end = currentPage * recordsPerPage;
        }

        for (int i = begin; i < end; i++) {
            result.add(cities.get(i));
        }
        return result;
    }
}
