
package controllers.customer;

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
		Customer customer;

		volume = this.volumeService.findOne(varId);
		customer = (Customer) this.actorService.findByPrincipal();

		result = new ModelAndView("volume/subscribe");
		result.addObject("volume", volume);
		result.addObject("customer", customer);
		result.addObject("requestURI", "volume/customer/subscribe.do");

		return result;
	}

	@RequestMapping(value = "/subscribe", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Volume volume, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = new ModelAndView("redirect:/volume/list.do");
		else
			try {
				this.volumeService.saveSubscribe(volume);
				result = new ModelAndView("redirect:/volume/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/volume/list.do");
			}
		return result;
	}
}
