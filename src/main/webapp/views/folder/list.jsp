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

<%-- Stored message variables --%>

<spring:message code="folder.name" var="name" />
<spring:message code="folder.parent" var="parent" />
<spring:message code="folder.children" var="children" />
<spring:message code="folder.mailMessage.text" var="mailMessage" />
<spring:message code="folder.create" var="create" />
<spring:message code="folder.delete" var="delete" />
<spring:message code="folder.edit" var="edit" />
<spring:message code="folder.confirm.delete" var="confirm" />
<spring:message code="mailMessage.create" var="createMailMessage" />
<spring:message code="mailMessage.broadcast" var="broadcast" />

<security:authorize access="isAuthenticated()">

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="folders" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="name" title="${name}" sortable="true" />

	<display:column property="parent.name" title="${parent}" sortable="true" />

	<%-- Links towards display, apply, edit and cancel views --%>

	<spring:url var="mailMessageUrl" value="mailMessage/list.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column>
		<a href="${mailMessageUrl}"><jstl:out value="${mailMessage}" /></a>
	</display:column>
	
	<spring:url var="childrenListUrl" value="folder/childrenList.do">
		<spring:param name="folderId" value="${row.id}" />
	</spring:url>
	
	<display:column>
		<a href="${childrenListUrl}"><jstl:out value="${children}" /></a>
	</display:column>

	<display:column>
	<jstl:if test="${!row.system}">
		<spring:url var="editUrl" value="folder/edit.do">
			<spring:param name="folderId" value="${row.id}" />
		</spring:url>

		
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		
		
		<spring:url var="deleteUrl" value="folder/delete.do">
			<spring:param name="folderId" value="${row.id}" />
		</spring:url>

		
			<a href="${deleteUrl}" onclick="return confirm('${confirm}')" ><jstl:out value="${delete}" /></a>
		
		
	</jstl:if>
	</display:column>
	
</display:table>

<spring:url var="createUrl" value="folder/create.do"/>
	<a href="${createUrl}"><jstl:out value="${create}"/></a>

<spring:url var="createMailMessageUrl" value="mailMessage/create.do"/>
	<a href="${createMailMessageUrl}"><jstl:out value="${createMailMessage}"/></a>
	
<security:authorize access="hasRole('ADMIN')">
	<spring:url var="broadcastUrl" value="mailMessage/administrator/create.do"/>
		<a href="${broadcastUrl}"><jstl:out value="${broadcast}"/></a>
</security:authorize>
	
</security:authorize>