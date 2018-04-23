<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" height="200" width="600" alt="Acme, Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>

			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="user/create.do"><spring:message
								code="master.page.register.user" /></a></li>
				</ul></li>

		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>


					<security:authorize access="hasRole('ADMIN')">
						<li><a href="administrator/dashboard.do"><spring:message
									code="master.page.dashboard" /></a></li>
					</security:authorize>

					<security:authorize access="hasRole('USER')">

						<li><a href="newspaper/user/list.do"><spring:message
									code="master.page.list.newspaperUser" /></a></li>
						<li><a href="newspaper/listNotPublished.do"><spring:message
									code="master.page.create.article" /></a></li>

					</security:authorize>

					<security:authorize access="hasRole('CUSTOMER')">

					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>

				</ul></li>
		</security:authorize>

		<security:authorize access="permitAll">
			<li><a href="newspaper/listPublished.do"><spring:message
						code="master.page.list.newspaper" /></a></li>
			<li><a href="user/list.do"><spring:message
						code="master.page.list.user" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.search" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="article/search.do"><spring:message
								code="master.page.article.search" /></a></li>
					<li><a href="newspaper/search.do"><spring:message
								code="master.page.newspaper.search" /></a>
					<li>
				</ul></li>

		</security:authorize>

	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

