
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.NewspaperService;
import controllers.AbstractController;
import domain.Newspaper;
import domain.User;

@Controller
@RequestMapping("newspaper/user")
public class NewspaperUserController extends AbstractController {

	//Services

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private ActorService		actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Newspaper> newspapers;
		final boolean forCreate = false;
		newspapers = ((User) this.actorService.findByPrincipal()).getNewspapers();

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/list.do");
		result.addObject("forCreate", forCreate);
		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Newspaper newspaper;

		newspaper = this.newspaperService.create();
		result = this.createEditModelAndView(newspaper);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int newspaperId) {
		final ModelAndView result;
		Newspaper newspaper;

		newspaper = this.newspaperService.findOne(newspaperId);
		Assert.notNull(newspaper);
		result = this.createEditModelAndView(newspaper);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Newspaper newspaper, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(newspaper);
		else
			try {
				this.newspaperService.save(newspaper);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(newspaper, "newspaper.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Newspaper newspaper, final BindingResult binding) {
		ModelAndView result;

		try {
			this.newspaperService.delete(newspaper);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(newspaper, "newspaper.commit.error");
		}
		return result;
	}

	//Delete

	//	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	//	public ModelAndView delete(@RequestParam final int varId) {
	//		final ModelAndView result;
	//		Collection<Newspaper> newspapers;
	//		Newspaper newspaper;
	//
	//		result = new ModelAndView("newspaper/list");
	//		newspapers = ((User) this.actorService.findByPrincipal()).getNewspapers();
	//
	//		newspaper = this.newspaperService.findOne(varId);
	//
	//		this.newspaperService.delete(newspaper);
	//		newspapers = ((User) this.actorService.findByPrincipal()).getNewspapers();
	//
	//		result.addObject("socialIdentities", newspapers);
	//		result.addObject("requestURI", "newspaper/list.do");
	//
	//		return result;
	//	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final Integer varId) {

		ModelAndView result;
		Newspaper newspaper;
		newspaper = this.newspaperService.findOne(varId);

		try {
			this.newspaperService.delete(newspaper);
			result = new ModelAndView("redirect:/newspaper/listPublished.do");
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/newspaper/listPublished.do");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Newspaper newspaper) {
		ModelAndView result;

		result = this.createEditModelAndView(newspaper, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Newspaper newspaper, final String messageCode) {
		ModelAndView result;
		final Collection<Newspaper> newspapers;
		newspapers = ((User) this.actorService.findByPrincipal()).getNewspapers();

		result = new ModelAndView("newspaper/edit");
		result.addObject("newspaper", newspaper);
		result.addObject("newspapers", newspapers);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "newspaper/user/edit.do");

		return result;

	}
}
