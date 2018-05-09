
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

import repositories.UserRepository;
import security.Authority;
import security.UserAccount;
import domain.Article;
import domain.Chirp;
import domain.Folder;
import domain.Newspaper;
import domain.User;
import domain.Volume;
import forms.ActorRegisterForm;

@Service
@Transactional
public class UserService {

	//Managed repository ---------------------------------

	@Autowired
	private UserRepository	userRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService	actorService;

	@Autowired
	private FolderService	folderService;

	@Autowired
	private Validator		validator;


	//Simple CRUD Methods --------------------------------

	public User create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.USER);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));

		final User user = new User();
		user.setUserAccount(account);
		user.setArticles(new ArrayList<Article>());
		user.setChirps(new ArrayList<Chirp>());
		user.setFollowers(new ArrayList<User>());
		user.setNewspapers(new ArrayList<Newspaper>());
		user.setFolders(new ArrayList<Folder>());
		user.setVolumes(new ArrayList<Volume>());

		return user;
	}

	public Collection<User> findAll() {
		return this.userRepository.findAll();
	}

	public User findOne(final int id) {
		Assert.notNull(id);

		return this.userRepository.findOne(id);
	}

	public User save(final User user) {
		Assert.notNull(user);

		final User saved2;
		//Assertion that the user modifying this user has the correct privilege.
		if (user.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == user.getId());
			saved2 = this.userRepository.save(user);
		} else {
			final User saved = this.userRepository.save(user);
			saved.setFolders(this.folderService.generateDefaultFolders(saved));
			saved2 = this.userRepository.save(saved);
		}

		return saved2;
	}

	public void delete(final User user) {
		Assert.notNull(user);

		//Assertion that the user deleting this user has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == user.getId());

		this.userRepository.delete(user);
	}

	//Ancillary methods

	public User reconstruct(final ActorRegisterForm arf, final BindingResult binding) {
		User result;
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
		result.setArticles(new ArrayList<Article>());
		result.setChirps(new ArrayList<Chirp>());
		result.setFollowers(new ArrayList<User>());
		result.setNewspapers(new ArrayList<Newspaper>());
		result.setFolders(new ArrayList<Folder>());

		this.validator.validate(result, binding);

		return result;
	}

	public Double[] avgstdNewspapersPerUser() {
		return this.userRepository.avgstdNewspapersPerUser();
	}

	public Double[] avgstdArticlesPerWriter() {
		return this.userRepository.avgstdArticlesPerWriter();
	}

	public Double ratioUsersWithNewspaper() {
		return this.userRepository.ratioUsersWithNewspaper();
	}

	public Double ratioUsersWithArticle() {
		return this.userRepository.ratioUsersWithArticle();
	}

	public Double[] avgstdChirpsPerUser() {
		return this.userRepository.avgstdChirpsPerUser();
	}

	public Double usersAboveAvgChirps() {
		return this.userRepository.usersAboveAvgChirps();
	}

	public Double ratioPrivatePublicPerUser(final int id) {
		return this.userRepository.ratioPrivatePublicPerUser(id);
	}

	public Collection<Article> articlesPublishedPerUser(final int id) {
		return this.userRepository.articlesPublishedPerUser(id);
	}
}
