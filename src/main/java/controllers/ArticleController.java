
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import domain.Article;

@Controller
@RequestMapping("article")
public class ArticleController extends AbstractController {

	//Services

	@Autowired
	private ArticleService	articleService;


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

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		Article article;
		article = this.articleService.findOne(varId);

		result = new ModelAndView("article/display");
		result.addObject("article", article);
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
