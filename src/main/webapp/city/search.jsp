<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="/city/style.css"/>

<html>
<head>
    <title>Searching Page</title>
</head>
<body>
<div class="main">
    <div class="header">
        <h1>
            Searching Result
        </h1>
        <div>
            <a class="btn btn-secondary" href="/home">All cities</a>
            <a class="btn btn-success" href="/create">Add new city</a>
        </div>
    </div>

<%--    tim tiep neu in them, neu ko thi value = value truoc do = $inputString--%>
    <form class="form-inline d-flex justify-content-center md-form form-sm active-pink active-pink-2 mt-2"
          method="post" action="/find">
        <i class="fas fa-search" aria-hidden="true"></i>
        <input class="form-control form-control-sm ml-3 w-75" type="text" placeholder="Search" name="search_by_country"
               aria-label="Search" value="${inputString}">
        <input type="reset" value="">
    </form>

    <div align="center">
        <table class="table table-bordered table-striped">
            <thead class="grey lighten-2">
            <tr>
                <th scope="col">#</th>
                <th scope="col">City</th>
                <th scope="col">Nation</th>
                <th scope="col">Edit</th>
                <th scope="col">Delete</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="city" items="${cities}">
                <tr>
                    <td><c:out value="${city.id}"/></td>
                    <td><a href="/detail?id=${city.id}"><c:out value="${city.name}"/></a></td>
                    <td><c:out value="${city.nation}"/></td>
                    <td><a href="/edit?id=${city.id}">Edit</a></td>
                    <td><a href="/delete?id=${city.id}">Delete</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <nav aria-label="Navigation for countries">
            <ul class="pagination">
                <c:if test="${currentPage != 1}">
                    <li class="page-item"><a class="page-link"
                                             href="/find?page=${currentPage-1}&nation=${inputString}">Previous</a>
                    </li>
                </c:if>

                <c:set  var="start" scope="session" value="1"></c:set>
                <c:choose>
                    <c:when test="${noOfPages <= 3}">
                        <c:set  var="end" scope="session" value="${totalPages}"></c:set>
                    </c:when>
                    <c:otherwise>
                        <c:set  var="end" scope="session" value="${3}"></c:set>
                    </c:otherwise>
                </c:choose>
                <c:if test="${currentPage > 3}">
                    <c:set  var="start" scope="session" value="${currentPage-2}"></c:set>
                    <c:set  var="end" scope="session" value="${currentPage}"></c:set>
                </c:if>
                <c:forEach begin="${start}" end="${end}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active"><a class="page-link">
                                    ${i} <span class="sr-only">(current)</span></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="/find?page=${i}&nation=${inputString}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage lt totalPages}">
                    <li class="page-item"><a class="page-link"
                                             href="/find?page=${currentPage+1}&nation=${inputString}">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>