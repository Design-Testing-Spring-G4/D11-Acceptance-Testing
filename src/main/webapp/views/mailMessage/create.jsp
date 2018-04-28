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

<%-- Stored message variables --%>

<spring:message code="mailMessage.receiver" var="receiver" />
<spring:message code="mailMessage.priority" var="priority" />
<spring:message code="mailMessage.subject" var="subject" />
<spring:message code="mailMessage.body" var="body" />
<spring:message code="mailMessage.save" var="save" />
<spring:message code="mailMessage.cancel" var="cancel" />

<security:authorize access="isAuthenticated()">

<form:form action="${requestURI}" modelAttribute="mailMessage">

	<%-- Form fields --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sent" />
	<form:hidden path="sender" />
	<form:hidden path="folder" />
	
	<security:authorize access="hasRole('ADMIN')" >
		<jstl:if test="${requestURI == 'mailMessage/administrator/create.do'}" >
			<form:hidden path="receiver" />
		</jstl:if>
		
		<jstl:if test="${requestURI != 'mailMessage/administrator/create.do'}" >
			<form:label path="receiver">
				<jstl:out value="${receiver}" />:
			</form:label>
					<form:select path="receiver" >
						<form:option
							label="----"
							value="0" />
						<form:options 
							items="${receivers}" 
							itemLabel="userAccount.username"
							itemValue="id" />
					</form:select>
					<form:errors cssClass="error" path="receiver" />
			<br />
		</jstl:if>
	</security:authorize>
	
	<security:authorize access="!hasRole('ADMIN')" >
			<form:label path="receiver">
				<jstl:out value="${receiver}" />:
			</form:label>
					<form:select path="receiver" >
						<form:option
							label="----"
							value="0" />
						<form:options 
							items="${receivers}" 
							itemLabel="userAccount.username"
							itemValue="id" />
					</form:select>
					<form:errors cssClass="error" path="receiver" />
			<br />
	</security:authorize>
	
	<form:label path="priority">
		<jstl:out value="${priority}" />:
	</form:label>
			<form:select path="priority" >
				<form:option
					label="----"
					value="0" />
				<form:options 
					items="${priorities}" 
					itemLabel="id"
					itemValue="id" />
			</form:select>
			<form:errors cssClass="error" path="priority" />
	<br />
	
	<form:label path="subject">
		<jstl:out value="${subject}" />:
	</form:label>
			<form:input path="subject" />
			<form:errors cssClass="error" path="subject" />
	<br />
	
	<form:label path="body">
		<jstl:out value="${body}" />:
	</form:label>
			<form:textarea path="body" />
			<form:errors cssClass="error" path="body" />
	<br />
	
	<%-- Buttons --%>
	
	<security:authorize access="!hasRole('ADMIN')" >
		<input type="submit" name="save" value="${save}">
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')" >
		<jstl:if test="${requestURI == 'mailMessage/administrator/create.do'}" >
			<input type="submit" name="broadcast" value="${save}">
		</jstl:if>
		
		<jstl:if test="${requestURI != 'mailMessage/administrator/create.do'}" >
			<input type="submit" name="save" value="${save}">
		</jstl:if>
	</security:authorize>
	
	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('folder/list.do');" />

</form:form>

</security:authorize>