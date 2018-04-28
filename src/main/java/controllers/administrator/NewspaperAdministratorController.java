
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NewspaperService;
import controllers.AbstractController;
import domain.Newspaper;

@Controller
@RequestMapping("newspaper/user")
public class NewspaperAdministratorController extends AbstractController {

	//Services

	@Autowired
	private NewspaperService	newspaperService;


	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer varId) {
		ModelAndView result;
		Newspaper newspaper;
		newspaper = this.newspaperService.findOne(varId);

		try {
			this.newspaperService.delete(newspaper);
			result = new ModelAndView("redirect:/newspaper/list.do");
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/newspaper/list.do");
		}
		return result;
	}
}
