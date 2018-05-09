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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('CUSTOMER')">

	<form:form action="${requestURI}" modelAttribute="subscription">

		<%-- Form fields --%>
		
		<form:hidden path="volume" />
		
		<acme:textbox path="creditCard.holder" code="subscription.holder" />
		<br/>
		<acme:textbox path="creditCard.brand" code="subscription.brand" />
		<br/>
		<acme:textbox path="creditCard.number" code="subscription.number" />
		<br/>
		<acme:textbox path="creditCard.expMonth" code="subscription.expMonth" />
		<br/>
		<acme:textbox path="creditCard.expYear" code="subscription.expYear" />
		<br/>
		<acme:textbox path="creditCard.cvv" code="subscription.cvv" />
		<br/>
		
		<%-- Buttons --%>
		
		<acme:submit name="save" code="subscription.save" />
		<acme:cancel code="subscription.cancel" url="volume/list.do" />
	
	</form:form>

</security:authorize>