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

<spring:message code="newspaper.isPrivate" var="msgIsPrivate" />

<security:authorize access="hasRole('USER')">

	<form:form action="${requestURI}" modelAttribute="newspaper">

		<%-- Form fields --%>

		<form:hidden path="id" />
		
		<acme:textarea path="title" code="newspaper.title" />
		<br/>
		<br/>
		<acme:textarea path="publicationDate" code="newspaper.publicationDate" placeholder="dd/MM/yy"/>
		<br/>
		<br/>
		<acme:textarea path="description" code="newspaper.description" placeholder="dd/MM/yy"/>
		<br/>
		<br/>
		<acme:textarea path="picture" code="newspaper.picture" />
		<br/>
		<br/>
		<form:label path="isPrivate">
			<jstl:out value="${msgIsPrivate}" />:</form:label>
		<form:select path="isPrivate">
			<form:option label="NO" value="false" />
			<form:option label="YES" value="true" />
		</form:select>
		<br/>
		<br/>
			
		<%-- Buttons --%>
		
		<acme:submit name="save" code="newspaper.save" />
		<acme:cancel code="newspaper.cancel" url="newspaper/user/list.do" />

	</form:form>

</security:authorize>