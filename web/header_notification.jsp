<%-- 
    Document   : header_notification
    Created on : Nov 4, 2025, 3:07:31?AM
    Author     : vuthienkhiem
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">
</head>

<div class="notification-area d-inline-block me-3" style="position: relative;">
    <a href="#" id="notificationToggle" style="color: white; font-size: 20px;">
        <i class="fa fa-bell"></i>
        <c:if test="${unreadCount > 0}">
            <span class="badge bg-danger"
                  style="position:absolute; top:-5px; right:-10px; border-radius:50%; font-size:10px;">
                ${unreadCount}
            </span>
        </c:if>
    </a>

    <div id="notificationMenu" class="notification-dropdown"
         style="display:none; position:absolute; right:0; background:white; color:black;
                border-radius:10px; box-shadow:0 2px 10px rgba(0,0,0,0.2); width:300px; z-index:1000;">
        <ul style="list-style:none; margin:0; padding:10px;">
            <c:choose>
                <c:when test="${empty notifications}">
                    <li>Không có thông báo nào.</li>
                </c:when>
                <c:otherwise>
                    <c:forEach var="n" items="${notifications}">
                        <li style="padding:8px 0; border-bottom:1px solid #ddd;">
                            <i class="fa fa-info-circle text-primary"></i>
                            ${n.message}
                            <br>
                            <small class="text-muted">${n.sentDate}</small>
                        </li>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const toggle = document.getElementById("notificationToggle");
        const menu = document.getElementById("notificationMenu");
        toggle.addEventListener("click", function (e) {
            e.preventDefault();
            menu.style.display = (menu.style.display === "none" || menu.style.display === "")
                ? "block" : "none";
        });
        document.addEventListener("click", function (e) {
            if (!toggle.contains(e.target) && !menu.contains(e.target)) {
                menu.style.display = "none";
            }
        });
    });
</script>
