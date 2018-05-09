
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import controllers.AbstractController;
import domain.Actor;
import domain.Folder;

@Controller
@RequestMapping("folder")
public class FolderController extends AbstractController {

	//Services

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Folder> folders;

		folders = this.actorService.findByPrincipal().getFolders();

		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);
		result.addObject("requestURI", "folder/list.do");

		return result;
	}

	@RequestMapping(value = "/childrenList", method = RequestMethod.GET)
	public ModelAndView childrenList(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Folder> folders;
		Folder folder;

		folder = this.folderService.findOne(varId);
		folders = folder.getChildren();

		result = new ModelAndView("folder/childrenList");
		result.addObject("folders", folders);
		result.addObject("requestURI", "folder/childrenList.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Folder folder;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		folder = this.folderService.create(actor);
		result = this.createEditModelAndView(folder);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Folder folder;

		folder = this.folderService.findOne(varId);
		Assert.notNull(folder);
		result = this.createEditModelAndView(folder);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Folder f, final BindingResult binding) {
		ModelAndView result;
		final Folder folder = this.folderService.reconstruct(f, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(folder);
		else
			try {
				this.folderService.save(folder);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folder, "folder.commit.error");
			}
		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		final ModelAndView result;
		final Folder folder = this.folderService.findOne(varId);

		if (!folder.getChildren().isEmpty())
			result = new ModelAndView("redirect:list.do");
		else {
			this.folderService.delete(folder);

			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Folder folder) {
		ModelAndView result;

		result = this.createEditModelAndView(folder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder, final String messageCode) {
		ModelAndView result;
		final Collection<Folder> folders = this.actorService.findByPrincipal().getFolders();

		result = new ModelAndView("folder/edit");
		result.addObject("folder", folder);
		result.addObject("folders", folders);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "folder/edit.do");

		return result;

	}
}
