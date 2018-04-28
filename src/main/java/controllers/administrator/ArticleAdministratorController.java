
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.NewspaperService;
import controllers.AbstractController;
import domain.Article;
import domain.Newspaper;

@Controller
@RequestMapping("article/user")
public class ArticleAdministratorController extends AbstractController {

	//Services

	@Autowired
	private ArticleService		articleService;

	@Autowired
	private NewspaperService	newspaperService;


	//Deletion

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer varId) {
		ModelAndView result;
		Article article;
		article = this.articleService.findOne(varId);
		final Newspaper newspaper = this.newspaperService.newspapersWhoContainsArticle(varId);

		try {
			this.articleService.delete(article, newspaper.getId());
			result = new ModelAndView("redirect:/article/list.do");
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/article/list.do");
		}
		return result;
	}
}
