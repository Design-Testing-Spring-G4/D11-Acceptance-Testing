<%--
 * edit.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('USER')">

	<form:form action="${requestURI}" modelAttribute="volume">

		<%-- Form fields --%>

		<acme:textbox path="title" code="volume.title" />
		<br/>
		<acme:textbox path="description" code="volume.description" />
		<br/>
		<acme:textbox path="year" code="volume.year" />
		<br/>
		
		<%-- Buttons --%>
		
		<acme:submit name="save" code="volume.save" />
		<acme:cancel code="volume.cancel" url="volume/list.do" />

	</form:form>

</security:authorize>