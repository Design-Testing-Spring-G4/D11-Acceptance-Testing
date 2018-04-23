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

<spring:message code="newspaper.title" var="msgTitle" />
<spring:message code="newspaper.publicationDate"
	var="msgPublicationDate" />
<spring:message code="newspaper.description" var="msgDescription" />
<spring:message code="newspaper.picture" var="msgPicture" />
<spring:message code="newspaper.isPrivate" var="msgIsPrivate" />
<spring:message code="newspaper.save" var="msgSave" />
<spring:message code="newspaper.cancel" var="msgCancel" />

<security:authorize access="isAuthenticated()">

	<form:form action="${requestURI}" modelAttribute="newspaper">

		<%-- Form fields --%>

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="publisher" />
		<form:hidden path="customers" />
		<form:hidden path="articles" />

		<security:authorize access="hasRole('USER')">

			<form:label path="title">
				<jstl:out value="${msgTitle}" />:</form:label>
			<form:textarea path="title" />
			<form:errors cssClass="error" path="title" />
			<br />
			<br />

			<form:label path="publicationDate">
				<jstl:out value="${msgPublicationDate}" />:</form:label>
			<form:textarea path="publicationDate" placeholder="dd/MM/yy" />
			<form:errors cssClass="error" path="publicationDate" />
			<br />
			<br />

			<form:label path="description">
				<jstl:out value="${msgDescription}" />:</form:label>
			<form:textarea path="description" />
			<form:errors cssClass="error" path="description" />
			<br />
			<br />

			<form:label path="picture">
				<jstl:out value="${msgPicture}" />:</form:label>
			<form:textarea path="picture" />
			<form:errors cssClass="error" path="picture" />
			<br />
			<br />

			<form:label path="isPrivate">
				<jstl:out value="${msgIsPrivate}" />:</form:label>
			<form:select path="isPrivate">
				<form:option label="NO" value="false" />
				<form:option label="YES" value="true" />
			</form:select>
			<br />
			<br />
			
			<%-- Buttons --%>
			<input type="submit" name="save" value="${msgSave}">


			<input type="button" name="cancel" value="${msgCancel}"
				onclick="javascript: relativeRedir('newspaper/user/list.do');" />
		
		</security:authorize>

	</form:form>

</security:authorize>