
package controllers.agent;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdvertisementService;
import controllers.AbstractController;
import domain.Actor;
import domain.Advertisement;
import domain.Newspaper;

@Controller
@RequestMapping("newspaper/agent")
public class NewspaperAgentController extends AbstractController {

	//Services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdvertisementService	advertisementService;


	//Listing

	@RequestMapping(value = "/listAds", method = RequestMethod.GET)
	public ModelAndView listAds() {
		final ModelAndView result;
		final Set<Newspaper> newspapers = new HashSet<Newspaper>();
		final Actor actor = this.actorService.findByPrincipal();

		for (final Advertisement a : this.advertisementService.findWithAds(actor.getId()))
			newspapers.add(a.getNewspaper());

		result = new ModelAndView("newspaper/listAds");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/agent/listAds.do");

		return result;
	}

	@RequestMapping(value = "/listNoAds", method = RequestMethod.GET)
	public ModelAndView listNoAds() {
		final ModelAndView result;
		final Set<Newspaper> newspapers = new HashSet<Newspaper>();
		final Actor actor = this.actorService.findByPrincipal();

		for (final Advertisement a : this.advertisementService.findWithoutAds(actor.getId()))
			newspapers.add(a.getNewspaper());

		result = new ModelAndView("newspaper/listAds");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/agent/listNoAds.do");

		return result;
	}
}
