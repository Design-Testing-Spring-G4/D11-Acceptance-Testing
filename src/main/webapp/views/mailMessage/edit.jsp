<%--
 * create.jsp
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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Stored message variables --%>

<spring:message code="mailMessage.folder.name" var="name" />

<security:authorize access="isAuthenticated()">

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="folders" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="name" title="${name}" sortable="true" />

	<%-- Links towards display, apply, edit and cancel views --%>
	
	<acme:link code="mailMessage.move" url="mailMessage/move.do" id="${row.id}"/>
	
</display:table>

</security:authorize>