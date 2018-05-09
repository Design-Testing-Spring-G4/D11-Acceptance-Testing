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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Stored message variables --%>

<spring:message code="user.articles" var="msgListArticles" />

<%-- For the selected newspaper in the list received as model, display the following information: --%>
	
<security:authorize access="permitAll()">

	<acme:displayField path="user.name" code="user.name" />
	<br/>
	<acme:displayField path="user.surname" code="user.surname" />
	<br/>
	<acme:displayField path="user.email" code="user.email" />
	<br/>
	<acme:displayField path="user.phone" code="user.phone" />
	<br/>
	<acme:displayField path="user.address" code="user.address" />
	<br/>

	<spring:url var="listArticles" value="article/listByUser.do">
		<spring:param name="varId" value="${user.id}" />
	</spring:url>

	<jstl:out value="${msgListArticles}" />:
	<a href="${listArticles}"><jstl:out value="${user.name}" /></a>
	<br/>

</security:authorize>