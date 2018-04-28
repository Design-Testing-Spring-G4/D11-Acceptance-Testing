
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AgentRepository;
import security.Authority;
import security.UserAccount;
import domain.Advertisement;
import domain.Agent;
import domain.Folder;

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
}
