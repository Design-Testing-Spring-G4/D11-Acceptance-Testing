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

<%-- Stored message variables --%>

<spring:message code="mailMessage.sent" var="sent" />
<spring:message code="mailMessage.priority" var="priority" />
<spring:message code="mailMessage.receiver" var="receiver" />
<spring:message code="mailMessage.subject" var="subject" />
<spring:message code="mailMessage.display" var="display" />
<spring:message code="mailMessage.move" var="move" />
<spring:message code="mailMessage.delete" var="delete" />
<spring:message code="mailMessage.return" var="returnMsg" />

<security:authorize access="isAuthenticated()">

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="mailMessages" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column title="${sent}" sortable="true">
		<fmt:formatDate value="${row.sent}" type="BOTH"/>
	</display:column>
	
	<display:column property="priority" title="${priority}" sortable="true" />
	
	<display:column property="subject" title="${subject}" sortable="true" />

	<%-- Links towards display, apply, edit and cancel views --%>

	<spring:url var="displayUrl" value="mailMessage/display.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column>
	
	<spring:url var="moveUrl" value="mailMessage/edit.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<display:column>
		<a href="${moveUrl}"><jstl:out value="${move}" /></a>
	</display:column>
	
	<spring:url var="deleteUrl" value="mailMessage/delete.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<display:column>
		<a href="${deleteUrl}"><jstl:out value="${delete}" /></a>
	</display:column>
	
</display:table>

<spring:url var="returnUrl" value="folder/list.do"/>
<a href="${returnUrl}"><jstl:out value="${returnMsg}" /></a>

</security:authorize>