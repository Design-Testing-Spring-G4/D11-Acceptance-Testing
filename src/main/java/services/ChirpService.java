
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import domain.Chirp;

@Service
@Transactional
public class ChirpService {

	//Managed repository ---------------------------------

	@Autowired
	private ChirpRepository	chirpRepository;


	//Simple CRUD Methods --------------------------------

	public Chirp create() {
		final Chirp chirp = new Chirp();

		return chirp;
	}

	public Collection<Chirp> findAll() {
		return this.chirpRepository.findAll();
	}

	public Chirp findOne(final int id) {
		Assert.notNull(id);

		return this.chirpRepository.findOne(id);
	}

	public Chirp save(final Chirp chirp) {
		Assert.notNull(chirp);

		final Date moment = new Date(System.currentTimeMillis() - 1);
		chirp.setMoment(moment);

		final Chirp saved = this.chirpRepository.save(chirp);

		return saved;
	}

	public void delete(final Chirp chirp) {
		Assert.notNull(chirp);

		this.chirpRepository.delete(chirp);
	}
}
