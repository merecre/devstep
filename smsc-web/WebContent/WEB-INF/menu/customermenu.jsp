<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table width=100% class="sub" align="center">
	<tr>
		<td>
			<table align="center">
				<tr>
					<c:forEach items="${customermenus}" var="menu">
						<c:choose>
							<c:when test="${menu.pageIndex == sessionScope.cusMenu}">
								<c:set var="hrefClass" scope="session" value="active sub" />
							</c:when>
							<c:otherwise>
								<c:set var="hrefClass" scope="session" value="sub" />
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${not empty sessionScope.id}">
								<c:set var="menuLinks" scope="session"
									value="id=${sessionScope.id}" />
							</c:when>
							<c:otherwise>
								<c:set var="menuLinks" scope="session" value="" />
							</c:otherwise>
						</c:choose>

						<td class='sub'><a class='${hrefClass}'
							href="${menu.pageStr}?${menuLinks}&cusMenu=${menu.pageIndex}">${menu.title}</a>
						</td>
					</c:forEach>
				</tr>
			</table>
		</td>
	</tr>
</table>