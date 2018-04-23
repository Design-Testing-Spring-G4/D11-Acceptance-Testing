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


<spring:message code="user.name" var="name" />
<spring:message code="user.surname" var="surname" />
<spring:message code="user.email" var="email" />
<spring:message code="user.phone" var="phone" />
<spring:message code="user.address" var="address" />
<spring:message code="user.articles" var="msgListArticles" />

<%-- For the selected newspaper in the list received as model, display the following information: --%>
	
<security:authorize access="permitAll()">

	<jstl:out value="${name}" />:&nbsp;
	<jstl:out value="${user.name}" />
	<br />
	
	<jstl:out value="${surname}" />:&nbsp;
	<jstl:out value="${user.surname}" />
	<br />
	
	<jstl:out value="${email}" />:&nbsp;
	<jstl:out value="${user.email}" />
	<br />
	
	<jstl:out value="${phone}" />:&nbsp;
	<jstl:out value="${user.phone}" />
	<br />
	
	<jstl:out value="${address}" />:&nbsp;
	<jstl:out value="${user.address}" />
	<br />

	<spring:url var="listArticles" value="article/user/listByUser.do">
		<spring:param name="varId" value="${user.id}" />
	</spring:url>

	<jstl:out value="${msgListArticles}" />:
	<a href="${listArticles}"><jstl:out value="${user.name}" /></a>
	<br />
	
	
		


</security:authorize>