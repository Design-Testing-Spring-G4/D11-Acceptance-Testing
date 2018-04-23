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

<spring:message code="newspaper.title" var="title" />
<spring:message code="newspaper.publisher" var="publisher" />
<spring:message code="newspaper.publicationDate" var="publicationDate" />
<spring:message code="newspaper.description" var="description" />
<spring:message code="newspaper.isPrivate" var="isPrivate" />
<spring:message code="newspaper.display" var="display" />
<spring:message code="newspaper.create" var="msgCreate" />
<spring:message code="newspaper.dateInt" var="formatDate" />
<spring:message code="acrticle.create" var="msgCreateArticle" />
<spring:message code="newspaper.delete" var="msgDeleteNewspaper" />

<security:authorize access="permitAll()">

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="newspapers" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column property="title" title="${title}" />

		<display:column property="publisher.userAccount.username"
			title="${publisher}" />

		<display:column title="${publicationDate}">
			<fmt:formatDate value="${row.publicationDate}"
				pattern="${formatDate}" />
		</display:column>

		<display:column property="description" title="${description}" />

		<display:column property="isPrivate" title="${isPrivate}" />

		<%-- Links towards edition, display and others --%>

		<spring:url var="displayUrl" value="newspaper/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column>
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
		</display:column>


		<security:authorize access="hasRole('ADMIN')">
			<spring:url var="deleteUrl" value="newspaper/user/delete.do">
				<spring:param name="varId" value="${row.id}" />
			</spring:url>

			<display:column>
				<a href="${deleteUrl}"><jstl:out value="${msgDeleteNewspaper}" /></a>
			</display:column>
		</security:authorize>
		
		<jstl:if test="${forCreate == true}">
			<spring:url var="createArticle" value="article/user/create.do">
				<spring:param name="varId" value="${row.id}" />
			</spring:url>

			<display:column>
				<a href="${createArticle}"><jstl:out value="${msgCreateArticle}" /></a>
			</display:column>
		</jstl:if>
		
	
	</display:table>
	<security:authorize access="hasRole('USER')">
		<spring:url var="createUrl" value="newspaper/user/create.do" />
		<a href="${createUrl}"><jstl:out value="${msgCreate}" /></a>
	</security:authorize>

</security:authorize>
