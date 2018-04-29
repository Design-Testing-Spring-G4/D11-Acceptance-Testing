
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.VolumeRepository;
import security.Authority;
import domain.Customer;
import domain.Newspaper;
import domain.User;
import domain.Volume;

@Service
@Transactional
public class VolumeService {

	//Managed repository ---------------------------------

	@Autowired
	private VolumeRepository	volumeRepository;

	//Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private NewspaperService	newspaperService;


	//Simple CRUD Methods --------------------------------

	public Volume create() {
		final Volume volume = new Volume();

		final User publisher = (User) this.actorService.findByPrincipal();
		volume.setPublisher(publisher);
		volume.setNewspapers(new ArrayList<Newspaper>());
		volume.setSubscriptions(new ArrayList<Customer>());

		return volume;
	}

	public Collection<Volume> findAll() {
		return this.volumeRepository.findAll();
	}

	public Volume findOne(final int id) {
		Assert.notNull(id);

		return this.volumeRepository.findOne(id);
	}

	public Volume save(final Volume volume) {
		Assert.notNull(volume);

		//Assertion that the user modifying this volume has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == volume.getPublisher().getId());

		final Volume saved = this.volumeRepository.save(volume);

		return saved;
	}

	public Volume saveSubscribe(final Volume volume) {
		Assert.notNull(volume);

		//Assertion that the user modifying this volume is a subscribing customer.
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(Authority.CUSTOMER));

		final Collection<Customer> subscriptions = volume.getSubscriptions();
		subscriptions.add((Customer) this.actorService.findByPrincipal());
		volume.setSubscriptions(subscriptions);

		final Volume saved = this.volumeRepository.save(volume);

		for (final Newspaper n : saved.getNewspapers()) {
			final Collection<Customer> customers = n.getCustomers();
			if (!customers.contains(n)) {
				customers.add((Customer) this.actorService.findByPrincipal());
				n.setCustomers(customers);
				this.newspaperService.saveInternal(n);
			}
		}

		return saved;
	}
	public void delete(final Volume volume) {
		Assert.notNull(volume);

		//Assertion that the user deleting this volume has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == volume.getPublisher().getId());

		this.volumeRepository.delete(volume);
	}

	//Other methods

	public Double avgNewspapersPerVolume() {
		return this.volumeRepository.avgNewspapersPerVolume();
	}

	public Double ratioSubscriptionsVolumesNewspapers() {
		return this.volumeRepository.ratioSubscriptionsVolumesNewspapers();
	}
}
