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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>


<spring:message code="article.title" var="articleTitle" />
<spring:message code="article.moment" var="moment" />
<spring:message code="article.summary" var="summary" />
<spring:message code="article.body" var="body" />
<spring:message code="article.finalMode" var="finalMode" />
<spring:message code="article.dateInt" var="formatDate" />

<%-- For the selected newspaper in the list received as model, display the following information: --%>
	
<security:authorize access="permitAll()">

	<jstl:out value="${articleTitle}" />:&nbsp;
	<jstl:out value="${article.title}" />
	<br />
	
	<jstl:out value="${moment}" />:&nbsp;
	<fmt:formatDate value="${article.moment}" pattern="${formatDate}" />
	<br />
	
	<jstl:out value="${summary}" />:&nbsp;
	<jstl:out value="${article.summary}" />
	<br />
	
	<jstl:out value="${body}" />:&nbsp;
	<jstl:out value="${article.body}" />
	<br />
	
	<jstl:out value="${finalMode}" />:&nbsp;
	<jstl:out value="${article.finalMode}" />
	<br />
	
	<jstl:if test="${newspaper.picture != ''}">
		<img src="${newspaper.picture}" />
<%-- 		<jstl:forEach var="picture" items="${newspaper.picture}">
		<img src="${newspaper.picture}" />
		</jstl:forEach>
		<br /> --%>
	</jstl:if>
	

</security:authorize>