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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Stored message variables --%>

<spring:message code="volume.title" var="title" />
<spring:message code="volume.description" var="description" />
<spring:message code="volume.year" var="year" />
<spring:message code="volume.publisher" var="publisher" />
<spring:message code="volume.create" var="create" />

<security:authorize access="permitAll()">

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="volumes" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column property="title" title="${title}" />
		
		<display:column property="description" title="${description}" />
		
		<display:column property="year" title="${year}" />
		
		<display:column property="publisher.name" title="${publisher}" />

		<%-- Links towards edition, display and others --%>

		<acme:link code="volume.browse" url="volume/listNewspapers.do" id="${row.id}" />
			
		<security:authorize access="hasRole('USER')">
			<acme:link code="volume.edit" url="volume/user/edit.do" id="${row.id}" />
		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">
			<acme:link code="volume.subscribe" url="subscription/customer/create.do" id="${row.id}" />
		</security:authorize>

	</display:table>

	<security:authorize access="hasRole('USER')">
		<spring:url var="createUrl" value="volume/user/create.do" />
		<a href="${createUrl}"><jstl:out value="${create}" /></a>
	</security:authorize>

</security:authorize>
