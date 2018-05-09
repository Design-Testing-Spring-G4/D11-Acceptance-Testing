
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AgentService;
import domain.Agent;
import forms.ActorRegisterForm;

@Controller
@RequestMapping("agent")
public class AgentController extends AbstractController {

	//Services

	@Autowired
	private AgentService	agentService;

	@Autowired
	private ActorService	actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		ActorRegisterForm arf;

		arf = new ActorRegisterForm();
		result = this.createEditModelAndView(arf);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ActorRegisterForm arf, final BindingResult binding) {
		ModelAndView result;
		final Agent agent = this.agentService.reconstruct(arf, binding);

		if (binding.hasErrors()) {
			arf.setAcceptedTerms(false);
			result = this.createEditModelAndView(arf);
		} else
			try {
				this.actorService.hashPassword(agent);
				this.agentService.save(agent);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(arf, "agent.commit.error");
			}
		return result;
	}
	//Ancillary methods

	protected ModelAndView createEditModelAndView(final ActorRegisterForm arf) {
		ModelAndView result;

		result = this.createEditModelAndView(arf, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorRegisterForm arf, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("agent/create");
		result.addObject("arf", arf);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "agent/create.do");

		return result;

	}

}
