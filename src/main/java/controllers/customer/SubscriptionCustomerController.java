
package controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SubscriptionService;
import services.VolumeService;
import controllers.AbstractController;
import domain.Subscription;
import domain.Volume;

@Controller
@RequestMapping("subscription/customer")
public class SubscriptionCustomerController extends AbstractController {

	//Services

	@Autowired
	private SubscriptionService	subscriptionService;

	@Autowired
	private VolumeService		volumeService;


	//Subscription

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int varId) {
		final ModelAndView result;
		Volume volume;
		Subscription subscription;

		volume = this.volumeService.findOne(varId);
		subscription = this.subscriptionService.create();
		subscription.setVolume(volume);

		result = this.createEditModelAndView(subscription);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Subscription s, final BindingResult binding) {
		ModelAndView result;
		final Subscription subscription = this.subscriptionService.reconstruct(s, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(subscription, "subscription.commit.error");
		else
			try {
				this.subscriptionService.saveVolume(subscription);
				result = new ModelAndView("redirect:/volume/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(subscription, "subscription.commit.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Subscription subscription) {
		ModelAndView result;

		result = this.createEditModelAndView(subscription, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Subscription subscription, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("subscription/create");
		result.addObject("subscription", subscription);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "subscription/customer/create.do");

		return result;
	}
}
