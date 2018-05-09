<%--
 * display.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Stored message variables --%>

<spring:message code="article.moment" var="moment" />
<spring:message code="article.dateInt" var="formatDate" />

<%-- For the selected article in the list received as model, display the following information: --%>
	
<security:authorize access="permitAll()">
	
	<jstl:if test="${advertisement != null}">
		<a href="${advertisement.target}">
			<img src="${advertisement.banner}" height="150" width="550">
		</a>
		<hr/>
	</jstl:if>
	
	<acme:displayField code="article.title" path="article.title" />
	<br/>
	<jstl:out value="${moment}" />:
	<fmt:formatDate value="${article.moment}" pattern="${formatDate}" />
	<br/>
	<acme:displayField code="article.summary" path="article.summary" />
	<br/>
	<acme:displayField code="article.body" path="article.body" />
	<br/>
	
 	<jstl:forEach var="picture" items="${pictures}">
		<img src="${picture}" height="200" width="300"/>
	</jstl:forEach>
	<br />

</security:authorize>