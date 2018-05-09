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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Stored message variables --%>

<spring:message code="article.title" var="title" />
<spring:message code="article.writer" var="writer" />
<spring:message code="article.moment" var="moment" />
<spring:message code="article.summary" var="summary" />
<spring:message code="article.body" var="body" />
<spring:message code="article.finalMode" var="finalMode" />
<spring:message code="article.dateInt" var="formatDate" />


<security:authorize access="permitAll()">

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="articles" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column property="title" title="${title}" />

		<display:column property="writer.name"
			title="${writer}" sortable="true" />

		<display:column title="${moment}" sortable="true">
			<fmt:formatDate value="${row.moment}" pattern="${formatDate}" />
		</display:column>

		<display:column property="summary" title="${summary}" maxLength="120" />

		<display:column property="finalMode" title="${finalMode}"
			sortable="true" />

		<%-- Links towards edition, display and others --%>
		
		<acme:link code="article.display" url="article/display.do" id="${row.id}" />

		<security:authorize access="hasRole('ADMIN')">
			<acme:link code="article.delete" url="article/user/delete.do" id="${row.id}" />
		</security:authorize>

	</display:table>

</security:authorize>