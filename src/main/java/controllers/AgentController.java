
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AgentService;
import domain.Agent;

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
		Agent agent;

		agent = this.agentService.create();
		result = this.createEditModelAndView(agent);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Agent agent, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(agent);
		else
			try {
				this.actorService.hashPassword(agent);
				this.agentService.save(agent);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(agent, "agent.commit.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Agent agent) {
		ModelAndView result;

		result = this.createEditModelAndView(agent, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Agent agent, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("agent/create");
		result.addObject("agent", agent);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "agent/create.do");

		return result;

	}

}
