
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.NewspaperService;
import services.VolumeService;
import controllers.AbstractController;
import domain.Newspaper;
import domain.Volume;

@Controller
@RequestMapping("volume/user")
public class VolumeUserController extends AbstractController {

	//Services

	@Autowired
	private VolumeService		volumeService;

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private ActorService		actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Volume volume;

		volume = this.volumeService.create();
		result = this.createEditModelAndView(volume);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Volume volume, final Integer varId, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(volume);
		else
			try {
				this.volumeService.save(volume);
				result = new ModelAndView("redirect:/volume/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(volume, "volume.commit.error");
			}
		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Newspaper> newspapers;
		Volume volume;

		newspapers = this.newspaperService.newspapersForToPublish();
		volume = this.volumeService.findOne(varId);

		if (this.actorService.findByPrincipal().getId() != volume.getPublisher().getId())
			result = new ModelAndView("redirect:/volume/list.do");
		else {
			result = new ModelAndView("volume/edit");
			result.addObject("newspapers", newspapers);
			result.addObject("volume", volume);
			result.addObject("requestURI", "volume/user/edit.do");
		}

		return result;
	}
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam final int varId, final int var2Id) {
		final ModelAndView result;
		final Volume volume = this.volumeService.findOne(varId);
		final Newspaper newspaper = this.newspaperService.findOne(var2Id);

		final Collection<Newspaper> newspapers = volume.getNewspapers();
		if (!newspapers.contains(newspaper)) {
			newspapers.add(newspaper);
			volume.setNewspapers(newspapers);
			this.volumeService.save(volume);
		}

		result = new ModelAndView("redirect:/volume/user/edit.do?varId=" + varId);

		return result;
	}

	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam final int varId, final int var2Id) {
		final ModelAndView result;
		final Volume volume = this.volumeService.findOne(varId);
		final Newspaper newspaper = this.newspaperService.findOne(var2Id);

		final Collection<Newspaper> newspapers = volume.getNewspapers();
		if (newspapers.contains(newspaper)) {
			newspapers.remove(newspaper);
			volume.setNewspapers(newspapers);
			this.volumeService.save(volume);
		}

		result = new ModelAndView("redirect:/volume/user/edit.do?varId=" + varId);

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Volume volume) {
		ModelAndView result;

		result = this.createEditModelAndView(volume, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Volume volume, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("volume/create");
		result.addObject("volume", volume);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "volume/user/create.do");

		return result;
	}
}
