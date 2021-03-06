<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="isAuthenticated()">

<form:form action="${requestURI}" modelAttribute="folder">

	<%-- Forms --%>
	
	<form:hidden path="id" />
	
	<acme:textbox path="name" code="folder.name" />
	<br/>
	<acme:select path="parent" code="folder.parent" items="${folders}" itemLabel="name" />
	<br/>
	
	<%-- Buttons --%>
	
	<acme:submit name="save" code="folder.save" />
	<acme:cancel code="folder.cancel" url="folder/list.do" />

</form:form>

</security:authorize>