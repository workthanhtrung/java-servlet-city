<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
      integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="/city/style.css"/>
<html>
<head>
    <title>${currentCity.name}</title>
</head>
<body>
<div class="main">
    <div class="header">
        <h1>
            ${currentCity.name} city
        </h1>
        <a class="btn btn-success" href="/home">All cities</a>
    </div>

    <div>
        <p>City: <span>${currentCity.name}</span></p>
        <p>Nation: <span>${currentCity.nation}</span></p>
        <p>Area: <span>${currentCity.area}</span></p>
        <p>Population: <span>${currentCity.population}</span></p>
        <p>GDP: <span>${currentCity.gdp}</span></p>
        <p>Description: </p>
        <p>${currentCity.description}<p>
    </div>
    <div class="footer">
        <a class="btn btn-info" href="/edit?id=${currentCity.id}">Edit</a>
        <a class="btn btn-warning" href="/delete?id=${currentCity.id}">Delete</a>
    </div>
</div>
</body>
</html>