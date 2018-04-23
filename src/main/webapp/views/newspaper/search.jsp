<%--
 * search.jsp
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

<%-- Stored message variables --%>

<spring:message code="newspaper.searchNewspaper" var="searchNewspaper" />
<spring:message code="newspaper.search.keyword" var="searchKeyword"/>
<spring:message code="newspaper.search.searchButton" var="search"/>

<form:form action="${requestURI}">
	
	<jstl:out value="${searchKeyword}" />
	<input type="text" name="keyword" id="keyword" />
	
	<input type="submit" name="results" value="${search}"/>&nbsp; 	
	
</form:form>