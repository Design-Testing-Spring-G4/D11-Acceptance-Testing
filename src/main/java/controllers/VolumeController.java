
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.VolumeService;
import domain.Newspaper;
import domain.Volume;

@Controller
@RequestMapping("volume")
public class VolumeController extends AbstractController {

	//Services

	@Autowired
	private VolumeService	volumeService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Volume> volumes;

		volumes = this.volumeService.findAll();

		result = new ModelAndView("volume/list");
		result.addObject("volumes", volumes);
		result.addObject("requestURI", "volume/list.do");

		return result;
	}

	@RequestMapping(value = "/listNewspapers", method = RequestMethod.GET)
	public ModelAndView listNewspapers(@RequestParam final int varId) {
		final ModelAndView result;
		Volume volume;

		volume = this.volumeService.findOne(varId);
		final Collection<Newspaper> newspapers = volume.getNewspapers();

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/list.do");

		return result;
	}
}
