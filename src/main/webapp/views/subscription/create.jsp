<%--
 * create.jsp
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

<spring:message code="subscription.holder" var="holder" />
<spring:message code="subscription.brand" var="brand" />
<spring:message code="subscription.number" var="number" />
<spring:message code="subscription.expMonth" var="expMonth" />
<spring:message code="subscription.expYear" var="expYear" />
<spring:message code="subscription.cvv" var="cvv" />
<spring:message code="subscription.save" var="save" />
<spring:message code="subscription.cancel" var="cancel" />

<security:authorize access="hasRole('CUSTOMER')">

	<form:form action="${requestURI}" modelAttribute="subscription">

		<%-- Form fields --%>

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="customer" />
		<form:hidden path="volume" />
		<form:hidden path="newspaper" />

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
			onclick="javascript: relativeRedir('volume/list.do');" />
	
	</form:form>

</security:authorize>