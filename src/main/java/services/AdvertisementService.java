
package services;

import java.util.Collection;
import java.util.Set;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdvertisementRepository;
import domain.Advertisement;
import domain.Agent;
import domain.CreditCard;
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

	@Autowired
	private Validator				validator;


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

	//Ancillary methods

	public boolean isTaboo(final Advertisement a) {
		boolean res = false;
		for (final TabooWord t : this.tabooWordService.findAll())
			if (a.getTitle().contains(t.getWord()) || a.getBanner().contains(t.getWord()) || a.getTarget().contains(t.getWord()))
				res = true;

		return res;
	}

	public Advertisement reconstruct(final Advertisement advertisement, final BindingResult binding) {
		Advertisement result;
		final Agent agent = (Agent) this.actorService.findByPrincipal();

		result = this.create();
		result.setTitle(advertisement.getTitle());
		result.setAgent(agent);
		result.setBanner(advertisement.getBanner());
		result.setCreditCard(advertisement.getCreditCard());
		result.setNewspaper(advertisement.getNewspaper());
		result.setTarget(advertisement.getTarget());
		result.setId(advertisement.getId());
		result.setVersion(advertisement.getVersion());

		this.validator.validate(result, binding);

		return result;
	}

	public Double ratioAdsWithTabooWord() {
		Double count = 0.0;

		for (final Advertisement a : this.advertisementRepository.findAll())
			if (this.isTaboo(a))
				count += 1.0;

		return this.advertisementRepository.ratioAdsWithTabooWord(count);
	}

	public Set<Advertisement> findWithAds(final int id) {
		return this.advertisementRepository.findWithAds(id);
	}

	public Set<Advertisement> findWithoutAds(final int id) {
		return this.advertisementRepository.findWithoutAds(id);
	}
}
