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

<spring:message code="article.title" var="msgTitle" />
<spring:message code="article.summary" var="msgSummary" />
<spring:message code="article.body" var="msgBody" />
<spring:message code="article.pictures" var="msgPictures" />
<spring:message code="article.finalMode" var="msgFinalMode" />
<spring:message code="article.save" var="msgSave" />
<spring:message code="article.cancel" var="msgCancel" />


<security:authorize access="isAuthenticated()">

	<form:form action="${requestURI}" modelAttribute="article">

		<%-- Form fields --%>

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="writer" />
		<form:hidden path="moment" />
		<form:hidden path="followups" />

		<security:authorize access="hasRole('USER')">

			<form:label path="title">
				<jstl:out value="${msgTitle}" />:</form:label>
			<form:textarea path="title" />
			<form:errors cssClass="error" path="title" />
			<br />
			<br />

			<form:label path="summary">
				<jstl:out value="${msgSummary}" />:</form:label>
			<form:textarea path="summary" />
			<form:errors cssClass="error" path="summary" />
			<br />
			<br />

			<form:label path="body">
				<jstl:out value="${msgBody}" />:</form:label>
			<form:textarea path="body" />
			<form:errors cssClass="error" path="body" />
			<br />
			<br />

			<form:label path="pictures">
				<jstl:out value="${msgPictures}" />:</form:label>
			<form:textarea path="pictures" />
			<form:errors cssClass="error" path="pictures" />
			<br />
			<br />

			<form:label path="finalMode">
				<jstl:out value="${msgFinalMode}" />:</form:label>
			<form:select path="finalMode">
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