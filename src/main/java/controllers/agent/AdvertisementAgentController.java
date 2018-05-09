
package controllers.agent;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdvertisementService;
import services.NewspaperService;
import controllers.AbstractController;
import domain.Advertisement;
import domain.Newspaper;

@Controller
@RequestMapping("advertisement/agent")
public class AdvertisementAgentController extends AbstractController {

	//Services

	@Autowired
	private AdvertisementService	advertisementService;

	@Autowired
	private NewspaperService		newspaperService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Advertisement advertisement;

		advertisement = this.advertisementService.create();
		result = this.createEditModelAndView(advertisement);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Advertisement a, final BindingResult binding) {
		ModelAndView result;
		Assert.notNull(a);
		final Advertisement advertisement = this.advertisementService.reconstruct(a, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(advertisement);
		else
			try {
				this.advertisementService.save(advertisement);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(advertisement, "advertisement.commit.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Advertisement advertisement) {
		ModelAndView result;

		result = this.createEditModelAndView(advertisement, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Advertisement advertisement, final String messageCode) {
		ModelAndView result;
		final Collection<Newspaper> newspapers = this.newspaperService.findAll();

		result = new ModelAndView("advertisement/create");
		result.addObject("advertisement", advertisement);
		result.addObject("newspapers", newspapers);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "advertisement/agent/create.do");

		return result;

	}
}
