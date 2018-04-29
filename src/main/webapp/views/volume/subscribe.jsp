<%--
 * subscribe.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%-- Stored message variables --%>

<spring:message code="volume.title" var="title" />

<security:authorize access="hasRole('CUSTOMER')">

<form:form id="form" action="${requestURI}" modelAttribute="customer">

	<%-- Forms --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="name" />
	<form:hidden path="surname" />
	<form:hidden path="email" />
	<form:hidden path="id" />
	<form:hidden path="id" />
	<form:hidden path="id" />
		
	<form:label path="title">
		<jstl:out value="${title}" />:
	</form:label>
			<form:input path="title" />
			<form:errors cssClass="error" path="title" />
	<br />
	
	<form:label path="banner">
		<jstl:out value="${banner}" />:
	</form:label>
			<form:input path="banner" />
			<form:errors cssClass="error" path="banner" />
	<br />
	
	<form:label path="target">
		<jstl:out value="${target}" />:
	</form:label>
			<form:input path="target" />
			<form:errors cssClass="error" path="target" />
	<br />
	
	<form:label path="creditCards">
		<jstl:out value="${creditCards}" />:
	</form:label>
	<form:select path="creditCards">
		<form:option label="----" value="0" />
		<form:options items="${newspapers}" itemLabel="title"/>
	</form:select>
	<form:errors cssClass="error" path="newspaper" />
	<br />

	<form:label path="creditCard.holder">
		<jstl:out value="${holder}" />:
	</form:label>
			<form:input path="creditCard.holder" />
			<form:errors cssClass="error" path="creditCard.holder" />
	<br />
	
	<form:label path="creditCard.brand">
		<jstl:out value="${brand}" />:
	</form:label>
			<form:input path="creditCard.brand" />
			<form:errors cssClass="error" path="creditCard.brand" />
	<br />
	
	<form:label path="creditCard.number">
		<jstl:out value="${number}" />:
	</form:label>
			<form:input path="creditCard.number" />
			<form:errors cssClass="error" path="creditCard.number" />
	<br />
	
	<form:label path="creditCard.expMonth">
		<jstl:out value="${expMonth}" />:
	</form:label>
			<form:input path="creditCard.expMonth" />
			<form:errors cssClass="error" path="creditCard.expMonth" />
	<br />
	
	<form:label path="creditCard.expYear">
		<jstl:out value="${expYear}" />:
	</form:label>
			<form:input path="creditCard.expYear" />
			<form:errors cssClass="error" path="creditCard.expYear" />
	<br />
	
	<form:label path="creditCard.cvv">
		<jstl:out value="${cvv}" />:
	</form:label>
			<form:input path="creditCard.cvv" />
			<form:errors cssClass="error" path="creditCard.cvv" />
	<br />
	
	<%-- Buttons --%>
	<input type="submit" name="save" value="${save}" /> 
	
	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('welcome/index.do');" />
		
</form:form>
</security:authorize>
