
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.VolumeRepository;
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


	//Simple CRUD Methods --------------------------------

	public Volume create() {
		final Volume volume = new Volume();

		final User publisher = (User) this.actorService.findByPrincipal();
		volume.setPublisher(publisher);
		volume.setNewspapers(new ArrayList<Newspaper>());

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
}
