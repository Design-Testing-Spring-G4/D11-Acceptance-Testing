
package controllers.actor;

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
	public ModelAndView childrenList(@RequestParam final int folderId) {
		final ModelAndView result;
		Collection<Folder> folders;
		Folder folder;

		folder = this.folderService.findOne(folderId);
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
	public ModelAndView edit(@RequestParam final int folderId) {
		final ModelAndView result;
		Folder folder;

		folder = this.folderService.findOne(folderId);
		Assert.notNull(folder);
		result = this.createEditModelAndView(folder);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result;

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

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Folder folder, final BindingResult binding) {
		ModelAndView result;

		try {
			this.folderService.delete(folder);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(folder, "folder.commit.error");
		}
		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int folderId) {
		final ModelAndView result;
		Collection<Folder> folders;
		Folder folder;
		result = new ModelAndView("folder/list");
		folders = this.actorService.findByPrincipal().getFolders();

		folder = this.folderService.findOne(folderId);
		if (!folder.getChildren().isEmpty())
			result.addObject("message", "folder.delete.error");
		else {
			this.folderService.delete(folder);
			folders = this.actorService.findByPrincipal().getFolders();
		}

		result.addObject("folders", folders);
		result.addObject("requestURI", "folder/list.do");

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
