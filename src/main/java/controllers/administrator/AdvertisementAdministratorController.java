
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdvertisementService;
import controllers.AbstractController;
import domain.Advertisement;

@Controller
@RequestMapping("advertisement/administrator")
public class AdvertisementAdministratorController extends AbstractController {

	//Services

	@Autowired
	private AdvertisementService	advertisementService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Advertisement> advertisements = new ArrayList<Advertisement>();

		for (final Advertisement a : this.advertisementService.findAll())
			if (this.advertisementService.isTaboo(a))
				advertisements.add(a);

		result = new ModelAndView("advertisement/list");
		result.addObject("advertisements", advertisements);
		result.addObject("requestURI", "advertisement/administrator/list.do");

		return result;
	}

	//Removing

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		final ModelAndView result;

		final Advertisement advertisement = this.advertisementService.findOne(varId);
		this.advertisementService.delete(advertisement);

		result = this.list();

		return result;
	}
}
