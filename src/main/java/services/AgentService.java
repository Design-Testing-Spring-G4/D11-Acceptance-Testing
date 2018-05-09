
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AgentRepository;
import security.Authority;
import security.UserAccount;
import domain.Advertisement;
import domain.Agent;
import domain.Folder;
import forms.ActorRegisterForm;

@Service
@Transactional
public class AgentService {

	//Managed repository ---------------------------------

	@Autowired
	private AgentRepository	agentRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService	actorService;

	@Autowired
	private FolderService	folderService;

	@Autowired
	private Validator		validator;


	//Simple CRUD Methods --------------------------------

	public Agent create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.AGENT);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));

		final Agent agent = new Agent();
		agent.setUserAccount(account);
		agent.setAdvertisements(new ArrayList<Advertisement>());
		agent.setFolders(new ArrayList<Folder>());

		return agent;
	}

	public Collection<Agent> findAll() {
		return this.agentRepository.findAll();
	}

	public Agent findOne(final int id) {
		Assert.notNull(id);

		return this.agentRepository.findOne(id);
	}

	public Agent save(final Agent agent) {
		Assert.notNull(agent);

		final Agent saved2;
		//Assertion that the agent modifying this agent has the correct privilege.
		if (agent.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == agent.getId());
			saved2 = this.agentRepository.save(agent);
		} else {
			final Agent saved = this.agentRepository.save(agent);
			saved.setFolders(this.folderService.generateDefaultFolders(saved));
			saved2 = this.agentRepository.save(saved);
		}

		return saved2;
	}

	public void delete(final Agent agent) {
		Assert.notNull(agent);

		//Assertion that the agent deleting this agent has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == agent.getId());

		this.agentRepository.delete(agent);
	}

	//Ancillary methods

	public Agent reconstruct(final ActorRegisterForm arf, final BindingResult binding) {
		Agent result;
		Assert.isTrue(arf.isAcceptedTerms());
		Assert.isTrue(arf.getPassword().equals(arf.getRepeatPassword()));

		result = this.create();
		result.getUserAccount().setUsername(arf.getUsername());
		result.getUserAccount().setPassword(arf.getPassword());
		result.setName(arf.getName());
		result.setSurname(arf.getSurname());
		result.setEmail(arf.getEmail());
		result.setPhone(arf.getPhone());
		result.setAddress(arf.getAddress());
		result.setAdvertisements(new ArrayList<Advertisement>());
		result.setFolders(new ArrayList<Folder>());

		this.validator.validate(result, binding);

		return result;
	}
}
