
package controllers.customer;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.VolumeService;
import controllers.AbstractController;
import domain.CreditCard;
import domain.Customer;
import domain.Volume;

@Controller
@RequestMapping("volume/customer")
public class VolumeCustomerController extends AbstractController {

	//Services

	@Autowired
	private VolumeService	volumeService;

	@Autowired
	private ActorService	actorService;


	//Subscription

	@RequestMapping(value = "/subscribe", method = RequestMethod.GET)
	public ModelAndView subscribe(final int varId) {
		final ModelAndView result;
		Volume volume;
		Collection<CreditCard> creditCards = new ArrayList<CreditCard>();

		volume = this.volumeService.findOne(varId);
		creditCards = ((Customer) this.actorService.findByPrincipal()).getCreditCards();

		result = new ModelAndView("volume/subscribe");
		result.addObject("volume", volume);
		result.addObject("creditCards", creditCards);
		result.addObject("requestURI", "volume/subscribe.do");

		return result;
	}

	@RequestMapping(value = "/subscribe", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Volume volume, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = new ModelAndView("redirect:/newspaper/list.do");
		else
			try {
				this.volumeService.saveSubscribe(volume);
				result = new ModelAndView("redirect:/newspaper/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/newspaper/list.do");
			}
		return result;
	}
}
