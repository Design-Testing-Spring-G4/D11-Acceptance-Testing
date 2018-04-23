<%--
 * list.jsp
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Stored message variables --%>

<spring:message code="user.name" var="name" />
<spring:message code="user.surname" var="surname" />
<spring:message code="user.email" var="email" />
<spring:message code="user.display" var="display" />


<security:authorize access="permitAll()">

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="users" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>
	
	<display:column property="name" title="${name}" sortable="true" />

	<display:column property="surname" title="${surname}" sortable="true" />
	
	<display:column property="email" title="${email}" sortable="true" />
	
	<spring:url var="displayUrl" value="user/display.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column>

	
</display:table>


</security:authorize>
