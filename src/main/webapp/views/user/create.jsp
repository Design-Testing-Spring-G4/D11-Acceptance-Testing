<%--
 * create.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="isAnonymous()">

<form:form id="form" action="${requestURI}" modelAttribute="arf">

	<acme:textbox code="user.userAccount.username" path="username"/>
	<br/>
	<acme:password code="user.userAccount.password" path="password"/>
	<br/>
	<acme:password code="user.repeatPassword" path="repeatPassword"/>
	<br/>
	<acme:textbox code="user.name" path="name"/>
	<br/>
	<acme:textbox code="user.surname" path="surname"/>
	<br/>
	<acme:textbox code="user.email" path="email"/>
	<br/>
	<acme:textbox code="user.phone" path="phone" placeholder="345345345"/>
	<br/>
	<acme:textbox code="user.address" path="address"/>
	<br/>

	<form:label path="acceptedTerms" >
		<spring:message code="user.terms.text" />
	</form:label>
	<a href="welcome/terms.do" target="_blank"><spring:message code="user.terms.link" /></a>
	<form:checkbox path="acceptedTerms" required="required"/>
	<form:errors path="acceptedTerms" cssClass="error" />
	<br/>
	
	<acme:submit name="save" code="user.save"/>
	<acme:cancel code="user.cancel" url="welcome/index.do"/>
		
</form:form>
</security:authorize>