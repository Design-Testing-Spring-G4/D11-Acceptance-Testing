/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.CustomerService;
import services.NewspaperService;
import services.UserService;
import controllers.AbstractController;
import domain.Newspaper;
import domain.User;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	//Supporting services

	@Autowired
	private UserService			userService;

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private ArticleService		articleService;

	@Autowired
	private CustomerService		customerService;


	//Dashboard

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;
		final Collection<String> newspapersAboveAvg = new ArrayList<String>();
		final Collection<String> newspapersUnderAvg = new ArrayList<String>();
		Double ratiosPerUser = 0.0;

		//Parse the collections to display the entities' names or other data.
		for (final Newspaper n : this.newspaperService.newspapersAboveAvg())
			newspapersAboveAvg.add(n.getTitle());

		for (final Newspaper n : this.newspaperService.newspapersUnderAvg())
			newspapersUnderAvg.add(n.getTitle());

		for (final User u : this.userService.findAll()) {
			final Double d = this.userService.ratioPrivatePublicPerUser(u.getId());
			ratiosPerUser += (d == null) ? 0.0 : d;
		}
		final Double ratioPrivatePublicPerUser = ratiosPerUser / this.userService.findAll().size();

		result = new ModelAndView("administrator/dashboard");

		result.addObject("avgstdNewspapersPerUser", Arrays.toString(this.userService.avgstdNewspapersPerUser()));
		result.addObject("avgstdArticlesPerWriter", Arrays.toString(this.userService.avgstdArticlesPerWriter()));
		result.addObject("avgstdArticlesPerNewspaper", Arrays.toString(this.newspaperService.avgstdArticlesPerNewspaper()));
		result.addObject("newspapersAboveAvg", newspapersAboveAvg);
		result.addObject("newspapersUnderAvg", newspapersUnderAvg);
		result.addObject("ratioUsersWithNewspaper", this.userService.ratioUsersWithNewspaper());
		result.addObject("ratioUsersWithArticle", this.userService.ratioUsersWithArticle());

		result.addObject("avgFollowupsPerArticle", this.articleService.avgFollowupsPerArticle());
		result.addObject("avgFollowupsPerArticleWeeksOne", this.articleService.avgFollowupsPerArticle());
		result.addObject("avgFollowupsPerArticleWeeksTwo", this.articleService.avgFollowupsPerArticle());
		result.addObject("avgstdChirpsPerUser", Arrays.toString(this.userService.avgstdChirpsPerUser()));
		result.addObject("usersAboveAvgChirps", this.userService.usersAboveAvgChirps());

		result.addObject("ratioPublicNewspapers", this.newspaperService.ratioPublicNewspapers());
		result.addObject("avgArticlesPerPrivateNewspaper", this.newspaperService.avgArticlesPerPrivateNewspaper());
		result.addObject("avgArticlesPerPublicNewspaper", this.newspaperService.avgArticlesPerPublicNewspaper());
		result.addObject("ratioCustomerSubscriber", this.customerService.ratioCustomerSubscriber());
		result.addObject("ratioPrivatePublicPerUser", ratioPrivatePublicPerUser);

		result.addObject("requestURI", "administrator/dashboard.do");

		return result;
	}
}
