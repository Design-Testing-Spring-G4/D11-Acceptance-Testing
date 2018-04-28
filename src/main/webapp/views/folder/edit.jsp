<%--
 * edit.jsp
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

<%-- Stored message variables --%>

<spring:message code="folder.create" var="create" />
<spring:message code="folder.edit" var="edit" />
<spring:message code="folder.name" var="name" />
<spring:message code="folder.parent" var="parent" />
<spring:message code="folder.save" var="save" />
<spring:message code="folder.delete" var="delete" />
<spring:message code="folder.confirm.delete" var="confirm" />
<spring:message code="folder.cancel" var="cancel" />

<security:authorize access="isAuthenticated()">

<form:form action="${requestURI}" modelAttribute="folder">

	<%-- Forms --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="system" />
	<form:hidden path="mailMessage" />
	<form:hidden path="actor" />
	<form:hidden path="children" />
	
	<form:label path="name">
		<jstl:out value="${name}" />:
	</form:label>
			<form:input path="name" />
			<form:errors cssClass="error" path="name" />
	<br />
	
	<form:label path="parent">
		<jstl:out value="${parent}" />:
	</form:label>
			<form:select path="parent" >
				<form:option
					label="----"
					value="0" />
				<form:options 
					items="${folders}" 
					itemLabel="name"
					itemValue="id" />
			</form:select>
			<form:errors cssClass="error" path="parent" />
	<br/>
	<br/>
	<%-- Buttons --%>

	<input type="submit" name="save"
		value="${save}" />&nbsp; 

		
	<jstl:if test="${folder.id!=0}">
		<input type="submit" name="delete"
			value="${delete}"
			onclick="return confirm('${confirm}')" />&nbsp;
	</jstl:if>	
	
	<input type="button" name="cancel"
		value="${cancel}"
		onclick="javascript: relativeRedir('folder/list.do');" />
</form:form>

</security:authorize>