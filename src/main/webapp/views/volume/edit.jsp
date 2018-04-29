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

<%-- Stored message variables --%>

<spring:message code="volume.current" var="current" />
<spring:message code="volume.title" var="title" />
<spring:message code="volume.publisher" var="publisher" />
<spring:message code="volume.add" var="add" />
<spring:message code="volume.remove" var="remove" />

<security:authorize access="hasRole('USER')">
	
	<jstl:out value="${current}" />:&nbsp;
	<jstl:forEach var="newspaper" items="${volume.newspapers}">
		<jstl:out value="${newspaper.title}" />,&nbsp;
	</jstl:forEach>
	
	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="newspapers" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column property="title" title="${title}" />
		
		<display:column property="publisher.name" title="${publisher}" />

		<%-- Links towards edition, display and others --%>

		<spring:url var="addUrl" value="volume/user/add.do">
			<spring:param name="varId" value="${volume.id}" />
			<spring:param name="var2Id" value="${row.id}" />
		</spring:url>

		<display:column>
			<a href="${addUrl}"><jstl:out value="${add}" /></a>
		</display:column>

		<spring:url var="removeUrl" value="volume/user/remove.do">
			<spring:param name="varId" value="${volume.id}" />
			<spring:param name="var2Id" value="${row.id}" />
		</spring:url>

		<display:column>
			<a href="${removeUrl}"><jstl:out value="${remove}" /></a>
		</display:column>

	</display:table>
	
</security:authorize>