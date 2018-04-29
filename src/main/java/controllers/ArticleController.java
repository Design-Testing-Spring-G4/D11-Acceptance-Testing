
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdvertisementService;
import services.ArticleService;
import services.UserService;
import domain.Advertisement;
import domain.Article;

@Controller
@RequestMapping("article")
public class ArticleController extends AbstractController {

	//Services

	@Autowired
	private ArticleService			articleService;

	@Autowired
	private UserService				userService;

	@Autowired
	private AdvertisementService	advertisementService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Article> articles;

		articles = this.articleService.findAll();

		result = new ModelAndView("newspaper/list");
		result.addObject("articles", articles);
		result.addObject("requestURI", "article/list.do");

		return result;
	}

	@RequestMapping(value = "/listByUser", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Article> articles;

		articles = this.userService.articlesPublishedPerUser(varId);

		result = new ModelAndView("article/list");
		result.addObject("articles", articles);
		result.addObject("requestURI", "article/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;
		Article article;
		Advertisement advertisement = null;
		article = this.articleService.findOne(varId);
		final Collection<Advertisement> ads = new ArrayList<Advertisement>();
		for (final Advertisement a : this.advertisementService.findAll())
			if (a.getNewspaper().getArticles().contains(article)) {
				ads.addAll(a.getNewspaper().getAdvertisements());
				break;
			}
		if (!ads.isEmpty()) {
			final int i = (int) (Math.random() * ads.size());
			advertisement = (Advertisement) ads.toArray()[i];
		}

		result = new ModelAndView("article/display");
		result.addObject("article", article);
		result.addObject("advertisement", advertisement);
		result.addObject("requestURI", "article/display.do");

		return result;
	}
	//Search
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		final ModelAndView result;

		result = new ModelAndView("article/search");

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "results")
	public ModelAndView results(@RequestParam final String keyword) {
		ModelAndView result;
		Collection<Article> articles;
		articles = this.articleService.findByKeyword(keyword);

		result = new ModelAndView("article/list");
		result.addObject("articles", articles);
		result.addObject("requestURI", "article/list.do");

		return result;
	}
}
