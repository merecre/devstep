<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/menubuilder.jsp" />

<div id='cssmenu'>
<ul>
<c:forEach items="${menues}" var="menu">
<li ${menu.elementClass}><a href="${pageContext.servletContext.contextPath}/${menu.pageStr}?id=${menu.pageIndex}${menu.elementSubIndex}"><span>${menu.title}</span></a>
    <ul>
    <c:forEach items="${submenues}" var="submenu">
        <c:choose>
          <c:when test="${not empty submenu.subElementSubIndex}">
	        <c:set var="menulink" scope="session" value="&subid=${submenu.subElementSubIndex}"/>
	      </c:when>
	      <c:otherwise>
	        <c:set var="menulink" scope="session" value="${submenu.subElementSubIndex}"/>
	      </c:otherwise>
        </c:choose>
        
        <c:if test="${submenu.mainMenuId == menu.mainMenuId}">
            <li ${menu.elementClass}><a href="${pageContext.servletContext.contextPath}/${menu.pageStr}?id=${menu.pageIndex}${menulink}"><span>${submenu.title}</span></a>
        </c:if>
    </c:forEach>
    </ul>
</c:forEach>
</ul>
</div>

<div id="toprightdiv">
        <a href='<%=request.getRequestURI()%>?ln=lv'><span><small>LV</small></span></a>
        <a href='<%=request.getRequestURI()%>?ln=ru'><span><small>RU</small></span></a>
        <a href='<%=request.getRequestURI()%>?ln=en'><span><small>EN</small></span></a>
</div>

