
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NewspaperService;
import domain.Newspaper;

@Controller
@RequestMapping("newspaper")
public class NewspaperController extends AbstractController {

	//Services

	@Autowired
	private NewspaperService	newspaperService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Newspaper> newspapers;

		newspapers = this.newspaperService.findAll();

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/list.do");

		return result;
	}

	@RequestMapping(value = "/listPublished", method = RequestMethod.GET)
	public ModelAndView listPublished() {
		final ModelAndView result;
		Collection<Newspaper> newspapers;
		newspapers = this.newspaperService.newspapersForToPublish();
		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/list.do");

		return result;
	}

	@RequestMapping(value = "/listNotPublished", method = RequestMethod.GET)
	public ModelAndView listNotPublished() {
		final ModelAndView result;
		Collection<Newspaper> newspapers;
		final boolean forCreate = true;
		newspapers = this.newspaperService.newspapersForNotToPublish();

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/list.do");
		result.addObject("forCreate", forCreate);

		return result;
	}
	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		Newspaper newspaper;
		System.out.println("id: " + varId);
		newspaper = this.newspaperService.findOne(varId);

		result = new ModelAndView("newspaper/display");
		result.addObject("newspaper", newspaper);
		result.addObject("requestURI", "newspaper/display.do");

		return result;
	}
	//Search 

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		final ModelAndView result;

		result = new ModelAndView("newspaper/search");

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "results")
	public ModelAndView results(@RequestParam final String keyword) {
		ModelAndView result;
		Collection<Newspaper> newspapers;
		newspapers = this.newspaperService.findByKeyword(keyword);

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/list.do");

		return result;
	}

}
