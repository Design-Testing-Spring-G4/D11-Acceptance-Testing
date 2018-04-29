<%--
 * display.jsp
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

<spring:message code="newspaper.title" var="title" />
<spring:message code="newspaper.description" var="description" />
<spring:message code="newspaper.publicationDate" var="publicationDate" />
<spring:message code="newspaper.return" var="msgReturn" />


<spring:message code="article.title" var="articleTitle" />
<spring:message code="article.writer" var="articleWriter" />
<spring:message code="article.summary" var="articleSummary" />
<spring:message code="article.edit" var="msgEditArticle" />
<spring:message code="article.delete" var="msgDelete" />

<%-- For the selected newspaper in the list received as model, display the following information: --%>

<security:authorize access="permitAll()">

	<jstl:out value="${title}" />:&nbsp;
	<jstl:out value="${newspaper.title}" />
	<br />

	<jstl:out value="${description}" />:&nbsp;
	<jstl:out value="${newspaper.description}" />
	<br />

	<jstl:out value="${publicationDate}" />:&nbsp;
	<fmt:formatDate value="${newspaper.publicationDate}"
		pattern="${formatDate}" />
	<br />

	<jstl:if test="${newspaper.picture != ''}">
		<img src="${newspaper.picture}" height="200" width="300"/>
		<br />
	</jstl:if>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="newspaper.articles" requestURI="${requestURI}" id="row">

		<spring:url var="articleUrl" value="article/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column>
			<jstl:choose>
				<jstl:when test="${newspaper.isPrivate eq true}">
					<security:authorize access="hasRole('CUSTOMER')">
						<a href="${articleUrl}"><jstl:out value="${row.title}" /></a>
					</security:authorize>
					<security:authorize access="!hasRole('CUSTOMER')">
						<jstl:out value="${row.title}" />
					</security:authorize>
				</jstl:when>
				<jstl:otherwise>
					<a href="${articleUrl}"><jstl:out value="${row.title}" /></a>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>

		<spring:url var="writerUrl" value="user/display.do">
			<spring:param name="varId" value="${row.writer.id}" />
		</spring:url>

		<display:column>
			<a href="${writerUrl}"><jstl:out value="${row.writer.name}" /></a>
		</display:column>

		<display:column property="summary" title="${articleSummary}"
			sortable="true" maxLength="120"/>

	</display:table>

	<a href="newspaper/list.do"><jstl:out value="${msgReturn}" /></a>

</security:authorize>