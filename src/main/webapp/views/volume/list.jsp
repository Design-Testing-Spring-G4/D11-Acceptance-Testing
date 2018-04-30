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

<spring:message code="volume.title" var="title" />
<spring:message code="volume.description" var="description" />
<spring:message code="volume.year" var="year" />
<spring:message code="volume.publisher" var="publisher" />
<spring:message code="volume.browse" var="browse" />
<spring:message code="volume.edit" var="edit" />
<spring:message code="volume.subscribe" var="subscribe" />
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

		<spring:url var="browseUrl" value="volume/listNewspapers.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column>
			<a href="${browseUrl}"><jstl:out value="${browse}" /></a>
		</display:column>
			
		<security:authorize access="hasRole('USER')">
			<spring:url var="editUrl" value="volume/user/edit.do">
				<spring:param name="varId" value="${row.id}" />
			</spring:url>
			
			<display:column>
				<a href="${editUrl}"><jstl:out value="${edit}" /></a>
			</display:column>
		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">
			<spring:url var="subscribeUrl" value="subscription/customer/create.do">
				<spring:param name="varId" value="${row.id}" />
			</spring:url>

			<display:column>
				<a href="${subscribeUrl}"><jstl:out value="${subscribe}" /></a>
			</display:column>
		</security:authorize>

	</display:table>

	<security:authorize access="hasRole('USER')">
		<spring:url var="createUrl" value="volume/user/create.do" />
		<a href="${createUrl}"><jstl:out value="${create}" /></a>
	</security:authorize>

</security:authorize>
