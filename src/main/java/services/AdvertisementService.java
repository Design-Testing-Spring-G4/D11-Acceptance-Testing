
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdvertisementRepository;
import domain.Advertisement;
import domain.Agent;
import domain.TabooWord;

@Service
@Transactional
public class AdvertisementService {

	//Managed repository ---------------------------------

	@Autowired
	private AdvertisementRepository	advertisementRepository;

	//Supporting services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private TabooWordService		tabooWordService;


	//Simple CRUD Methods --------------------------------

	public Advertisement create() {
		final Advertisement advertisement = new Advertisement();

		final Agent agent = (Agent) this.actorService.findByPrincipal();
		advertisement.setAgent(agent);

		return advertisement;
	}

	public Collection<Advertisement> findAll() {
		return this.advertisementRepository.findAll();
	}

	public Advertisement findOne(final int id) {
		Assert.notNull(id);

		return this.advertisementRepository.findOne(id);
	}

	public Advertisement save(final Advertisement advertisement) {
		Assert.notNull(advertisement);

		//Assertion that the user modifying this advertisement has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == advertisement.getAgent().getId());

		final Advertisement saved = this.advertisementRepository.save(advertisement);

		return saved;
	}

	public void delete(final Advertisement advertisement) {
		Assert.notNull(advertisement);

		//Assertion that the user deleting this advertisement has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == advertisement.getAgent().getId());

		this.advertisementRepository.delete(advertisement);
	}

	//Other methods

	public Double ratioAdsWithTabooWord() {
		int count = 0;

		for (final TabooWord t : this.tabooWordService.findAll())
			for (final Advertisement a : this.advertisementRepository.findAll())
				if (a.getTitle().contains(t.getWord()) || a.getBanner().contains(t.getWord()) || a.getTarget().contains(t.getWord()))
					count += 1;

		return this.advertisementRepository.ratioAdsWithTabooWord(count);
	}
}
