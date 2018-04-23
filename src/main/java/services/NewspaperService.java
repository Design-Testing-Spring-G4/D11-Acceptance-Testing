
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NewspaperRepository;
import domain.Article;
import domain.Customer;
import domain.Newspaper;
import domain.User;

@Service
@Transactional
public class NewspaperService {

	//Managed repository ---------------------------------

	@Autowired
	private NewspaperRepository	newspaperRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;


	//Simple CRUD Methods --------------------------------

	public Newspaper create() {
		final Newspaper newspaper = new Newspaper();

		newspaper.setArticles(new ArrayList<Article>());
		newspaper.setCustomers(new ArrayList<Customer>());
		newspaper.setPrivate(false);
		final User user = (User) this.actorService.findByPrincipal();
		newspaper.setPublisher(user);

		return newspaper;
	}

	public Collection<Newspaper> findAll() {
		return this.newspaperRepository.findAll();
	}

	public Newspaper findOne(final int id) {
		Assert.notNull(id);

		return this.newspaperRepository.findOne(id);
	}

	public Newspaper save(final Newspaper newspaper) {
		Assert.notNull(newspaper);

		//Assertion that the user deleting this newspaper has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == newspaper.getPublisher().getId());

		final Newspaper saved = this.newspaperRepository.save(newspaper);

		return saved;
	}

	//Internal save method to avoid concurrency issues
	public Newspaper saveInternal(final Newspaper newspaper) {
		Assert.notNull(newspaper);
		final Newspaper saved = this.newspaperRepository.save(newspaper);
		return saved;
	}

	public void delete(final Newspaper newspaper) {
		Assert.notNull(newspaper);

		//Assertion that the user deleting this newspaper has the correct privilege.
		Assert.notNull(newspaper);
		newspaper.getArticles().clear();
		this.newspaperRepository.delete(newspaper);
	}

	//Other methods

	public Collection<Newspaper> findByKeyword(final String word) {
		final Collection<Newspaper> res = new ArrayList<Newspaper>();

		for (final Newspaper n : this.findAll())
			if (n.getTitle().contains(word) || n.getDescription().contains(word))
				res.add(n);
		return res;
	}

	//Ancillary methods

	public Double[] avgstdArticlesPerNewspaper() {
		return this.newspaperRepository.avgstdArticlesPerNewspaper();
	}

	public Collection<Newspaper> newspapersAboveAvg() {
		return this.newspaperRepository.newspapersAboveAvg();
	}

	public Collection<Newspaper> newspapersUnderAvg() {
		return this.newspaperRepository.newspapersUnderAvg();
	}

	public Double ratioPublicNewspapers() {
		return this.newspaperRepository.ratioPublicNewspapers();
	}

	public Double avgArticlesPerPrivateNewspaper() {
		return this.newspaperRepository.avgArticlesPerPrivateNewspaper();
	}

	public Double avgArticlesPerPublicNewspaper() {
		return this.newspaperRepository.avgArticlesPerPublicNewspaper();
	}

	public Collection<Newspaper> newspapersForToPublish() {
		return this.newspaperRepository.newspapersForToPublish(new Date(System.currentTimeMillis()));
	}

	public Collection<Newspaper> newspapersForNotToPublish() {
		return this.newspaperRepository.newspapersForNotToPublish(new Date(System.currentTimeMillis()));
	}

	public Newspaper newspapersWhoContainsArticle(final int id) {
		return this.newspaperRepository.newspapersWhoContainsArticle(id);
	}

}
