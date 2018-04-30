
package services;

import java.util.Collection;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdvertisementRepository;
import domain.Advertisement;
import domain.Agent;
import domain.CreditCard;
import domain.Newspaper;
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
	private NewspaperService		newspaperService;

	@Autowired
	private TabooWordService		tabooWordService;


	//Simple CRUD Methods --------------------------------

	public Advertisement create(final int varId) {
		final Advertisement advertisement = new Advertisement();
		final Newspaper newspaper = this.newspaperService.findOne(varId);
		Assert.notNull(newspaper);
		advertisement.setNewspaper(newspaper);
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

		//Assertion that the credit card used doesn't expire during the current month.
		final LocalDate ld = LocalDate.now();
		final CreditCard cc = advertisement.getCreditCard();
		if (cc.getExpYear() == ld.getYear())
			Assert.isTrue(!(cc.getExpMonth() > ld.getMonthOfYear()));

		final Advertisement saved = this.advertisementRepository.save(advertisement);

		return saved;
	}

	public void delete(final Advertisement advertisement) {
		Assert.notNull(advertisement);

		this.advertisementRepository.delete(advertisement);
	}

	//Other methods

	public boolean isTaboo(final Advertisement a) {
		boolean res = false;
		for (final TabooWord t : this.tabooWordService.findAll())
			if (a.getTitle().contains(t.getWord()) || a.getBanner().contains(t.getWord()) || a.getTarget().contains(t.getWord()))
				res = true;

		return res;
	}

	public Double ratioAdsWithTabooWord() {
		Double count = 0.0;

		for (final Advertisement a : this.advertisementRepository.findAll())
			if (this.isTaboo(a))
				count += 1.0;

		return this.advertisementRepository.ratioAdsWithTabooWord(count);
	}
}
