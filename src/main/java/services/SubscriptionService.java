
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubscriptionRepository;
import domain.Customer;
import domain.Newspaper;
import domain.Subscription;

@Service
@Transactional
public class SubscriptionService {

	//Managed repository ---------------------------------

	@Autowired
	private SubscriptionRepository	subscriptionRepository;

	//Supporting services

	@Autowired
	private ActorService			actorService;


	//Simple CRUD Methods --------------------------------

	public Subscription create() {
		final Subscription subscription = new Subscription();

		final Customer c = (Customer) this.actorService.findByPrincipal();
		subscription.setCustomer(c);

		return subscription;
	}

	public Collection<Subscription> findAll() {
		return this.subscriptionRepository.findAll();
	}

	public Subscription findOne(final int id) {
		Assert.notNull(id);

		return this.subscriptionRepository.findOne(id);
	}

	public Subscription save(final Subscription subscription) {
		Assert.notNull(subscription);

		//Assertion that the user creating this subscription has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == subscription.getCustomer().getId());

		final Subscription saved = this.subscriptionRepository.save(subscription);

		return saved;
	}

	public Subscription saveVolume(final Subscription subscription) {
		Assert.notNull(subscription);

		//Assertion that the user creating this subscription has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == subscription.getCustomer().getId());

		final Subscription saved = this.subscriptionRepository.save(subscription);

		//Subscription to every newspaper in the volume.
		for (final Newspaper n : saved.getVolume().getNewspapers()) {
			final Subscription s = this.create();
			s.setCreditCard(saved.getCreditCard());
			s.setNewspaper(n);
			this.save(s);
		}

		return saved;
	}

	public void delete(final Subscription subscription) {
		Assert.notNull(subscription);

		//Assertion that the user deleting this subscription has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == subscription.getCustomer().getId());

		this.subscriptionRepository.delete(subscription);
	}

	//Ancillary methods

	public Double ratioSubscriptionsVolumesNewspapers() {
		return this.subscriptionRepository.ratioSubscriptionsVolumesNewspapers();
	}
}
