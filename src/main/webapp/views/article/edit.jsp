<%--
 * edit.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Stored message variables --%>

<spring:message code="article.finalMode" var="msgFinalMode" />

<security:authorize access="hasRole('USER')">

	<form:form action="${requestURI}" modelAttribute="article">

		<%-- Form fields --%>
		
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="writer"/>
		<form:hidden path="followups"/>
		<form:hidden path="moment"/>
		
		<acme:textarea path="title" code="article.title" />
		<br/>
		<br/>
		<acme:textarea path="summary" code="article.summary" />
		<br/>
		<br/>
		<acme:textarea path="body" code="article.body" />
		<br/>
		<br/>
		<acme:textarea path="pictures" code="article.pictures" />
		<br/>
		<br/>
			
		<form:label path="finalMode">
			<jstl:out value="${msgFinalMode}" />:</form:label>
		<form:select path="finalMode">
			<form:option label="NO" value="false" />
			<form:option label="YES" value="true" />
		</form:select>
		<br />
		<br />

		<%-- Buttons --%>
		
		<acme:submit name="save" code="article.save" />
		<acme:cancel code="article.cancel" url="newspaper/user/list.do" />

	</form:form>

</security:authorize>