
package controllers.agent;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.NewspaperService;
import controllers.AbstractController;
import domain.Newspaper;

@Controller
@RequestMapping("newspaper/agent")
public class NewspaperAgentController extends AbstractController {

	//Services

	@Autowired
	private NewspaperService	newspaperService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int varId) {
		final ModelAndView result;
		Collection<Newspaper> newspapers;

		newspapers = (varId == 0) ? this.newspaperService.findWithAds() : this.newspaperService.findWithoutAds();

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/agent/list.do");

		return result;
	}
}
