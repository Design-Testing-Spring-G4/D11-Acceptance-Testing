
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FollowupRepository;
import domain.Followup;

@Service
@Transactional
public class FollowupService {

	//Managed repository ---------------------------------

	@Autowired
	private FollowupRepository	followupRepository;


	//Simple CRUD Methods --------------------------------

	public Followup create() {
		final Followup followup = new Followup();

		followup.setPictures(new ArrayList<String>());

		return followup;
	}

	public Collection<Followup> findAll() {
		return this.followupRepository.findAll();
	}

	public Followup findOne(final int id) {
		Assert.notNull(id);

		return this.followupRepository.findOne(id);
	}

	public Followup save(final Followup followup) {
		Assert.notNull(followup);

		final Date moment = new Date(System.currentTimeMillis() - 1);
		followup.setMoment(moment);

		final Followup saved = this.followupRepository.save(followup);

		return saved;
	}

	public void delete(final Followup followup) {
		Assert.notNull(followup);

		this.followupRepository.delete(followup);
	}
}
