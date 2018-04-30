
package controllers.agent;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		Advertisement advertisement;
		final Newspaper newspaper = this.newspaperService.findOne(varId);
		advertisement = this.advertisementService.create(newspaper.getId());
		result = this.createEditModelAndView(advertisement);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Advertisement advertisement, final BindingResult binding) {
		ModelAndView result;

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
