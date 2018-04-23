
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.Authority;
import security.UserAccount;
import domain.Article;
import domain.Chirp;
import domain.Newspaper;
import domain.User;

@Service
@Transactional
public class UserService {

	//Managed repository ---------------------------------

	@Autowired
	private UserRepository	userRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService	actorService;


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

		//Assertion that the user modifying this user has the correct privilege.
		if (user.getId() != 0)
			Assert.isTrue(this.actorService.findByPrincipal().getId() == user.getId());

		final User saved = this.userRepository.save(user);

		return saved;
	}

	public void delete(final User user) {
		Assert.notNull(user);

		//Assertion that the user deleting this user has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == user.getId());

		this.userRepository.delete(user);
	}

	//Ancillary methods

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
