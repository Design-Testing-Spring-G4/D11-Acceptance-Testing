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

<security:authorize access="hasRole('AGENT')">

<form:form id="form" action="${requestURI}" modelAttribute="advertisement">

	<acme:textbox path="title" code="advertisement.title" />
	<br/>
	<acme:textbox path="banner" code="advertisement.banner" />
	<br/>
	<acme:textbox path="target" code="advertisement.target" />
	<br/>
	<acme:select path="newspaper" code="advertisement.newspaper"
		items="${newspapers}" itemLabel="title"/>
	<br/>
	<acme:textbox path="creditCard.holder" code="advertisement.creditCard.holder" />
	<br/>
	<acme:textbox path="creditCard.brand" code="advertisement.creditCard.brand" />
	<br/>
	<acme:textbox path="creditCard.number" code="advertisement.creditCard.number" />
	<br/>
	<acme:textbox path="creditCard.expMonth" code="advertisement.creditCard.expMonth" />
	<br/>
	<acme:textbox path="creditCard.expYear" code="advertisement.creditCard.expYear" />
	<br/>
	<acme:textbox path="creditCard.cvv" code="advertisement.creditCard.cvv" />
	<br/>
	
	<%-- Buttons --%>
	
	<acme:submit name="save" code="advertisement.save" />
	<acme:cancel code="advertisement.cancel" url="welcome/index.do" />

</form:form>
</security:authorize>
