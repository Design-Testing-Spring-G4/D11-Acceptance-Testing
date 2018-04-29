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

<%-- Stored message variables --%>

<spring:message code="volume.title" var="title" />
<spring:message code="volume.description" var="description" />
<spring:message code="volume.year" var="year" />
<spring:message code="volume.save" var="save" />
<spring:message code="volume.cancel" var="cancel" />

<security:authorize access="hasRole('USER')">

	<form:form action="${requestURI}" modelAttribute="volume">

		<%-- Form fields --%>

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="publisher" />
		<form:hidden path="newspapers" />
		<form:hidden path="subscriptions" />

		<form:label path="title">
			<jstl:out value="${title}" />:</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br />
		
		<form:label path="description">
			<jstl:out value="${description}" />:</form:label>
		<form:input path="description" />
		<form:errors cssClass="error" path="description" />
		<br />
		
		<form:label path="year">
			<jstl:out value="${year}" />:</form:label>
		<form:input path="year" />
		<form:errors cssClass="error" path="year" />
		<br />
		
		<%-- Buttons --%>
		<input type="submit" name="save" value="${save}">

		<input type="button" name="cancel" value="${cancel}"
			onclick="javascript: relativeRedir('newspaper/user/list.do');" />

	</form:form>

</security:authorize>