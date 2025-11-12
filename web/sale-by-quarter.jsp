<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="vi_VN" scope="session"/>

<div class="container">
    <h3>Bảng doanh thu theo quý</h3>
    <table border="1" cellpadding="8" cellspacing="0" style="border-collapse:collapse;width:100%;">
        <thead>
            <tr>
                <th>Năm</th>
                <th>Quý</th>
                <th>Số đơn</th>
                <th>Doanh thu</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="yearEntry" items="${revenueByQuarter}">
                <c:set var="year" value="${yearEntry.key}" />
                <c:forEach var="qEntry" items="${yearEntry.value}">
                    <tr>
                        <td>${year}</td>
                        <td>Q${qEntry.key}</td>
                        <td>${qEntry.value.count}</td>
                        <td>
                            <fmt:formatNumber value="${qEntry.value.revenue}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                        </td>
                    </tr>
                </c:forEach>
            </c:forEach>
        </tbody>
    </table>
</div>
