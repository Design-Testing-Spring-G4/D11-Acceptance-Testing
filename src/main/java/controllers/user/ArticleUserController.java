
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ArticleService;
import services.NewspaperService;
import services.UserService;
import controllers.AbstractController;
import domain.Article;
import domain.Newspaper;
import domain.User;

@Controller
@RequestMapping("article/user")
public class ArticleUserController extends AbstractController {

	//Services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserService			userService;

	@Autowired
	private ArticleService		articleService;

	@Autowired
	private NewspaperService	newspaperService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer varId) {
		final ModelAndView result;
		Article article;
		article = this.articleService.create(varId);
		result = this.createEditModelAndView(article, varId);
		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer varId) {
		final ModelAndView result;
		final Article article = this.articleService.findOne(varId);
		Assert.notNull(article);
		if (article.isFinalMode())
			result = new ModelAndView("redirect:/newspaper/user/list.do");
		else
			result = this.createEditModelAndView(article, varId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Article article, final Integer varId, final BindingResult binding) {
		ModelAndView result;
		System.out.println("error:" + binding.getAllErrors());
		if (binding.hasErrors())
			result = this.createEditModelAndView(article, varId);
		else
			try {
				this.articleService.save(article, varId);
				result = new ModelAndView("redirect:/newspaper/user/list.do");
			} catch (final Throwable oops) {
				System.out.println("oops:" + oops.getMessage());
				result = this.createEditModelAndView(article, varId, "article.commit.error");
				System.out.println(oops.getStackTrace());
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer varId) {

		ModelAndView result;
		Article article;
		article = this.articleService.findOne(varId);
		final Newspaper newspaper = this.newspaperService.newspapersWhoContainsArticle(varId);

		try {
			this.articleService.delete(article, newspaper.getId());
			result = new ModelAndView("redirect:/user/list.do");
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/user/list.do");
		}
		return result;
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Article> articles;

		articles = ((User) this.actorService.findByPrincipal()).getArticles();

		result = new ModelAndView("article/list");
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

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Article article, final Integer varId) {
		ModelAndView result;

		result = this.createEditModelAndView(article, varId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Article article, final Integer varId, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("article/edit");
		result.addObject("article", article);
		result.addObject("varId", varId);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "article/user/edit.do");

		return result;
	}
}
