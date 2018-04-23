
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ArticleRepository;
import domain.Article;
import domain.Followup;
import domain.Newspaper;
import domain.User;

@Service
@Transactional
public class ArticleService {

	//Managed repository ---------------------------------

	@Autowired
	private ArticleRepository	articleRepository;

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private ActorService		actorService;


	//Simple CRUD Methods --------------------------------

	public Article create(final int varId) {
		final Article article = new Article();
		final Newspaper newspaper = this.newspaperService.findOne(varId);
		Assert.notNull(newspaper);
		article.setWriter(((User) this.actorService.findByPrincipal()));
		article.setFinalMode(false);
		article.setFollowups(new ArrayList<Followup>());
		article.setPictures(new ArrayList<String>());
		article.setMoment(newspaper.getPublicationDate());
		return article;
	}

	public Collection<Article> findAll() {
		return this.articleRepository.findAll();
	}

	public Article findOne(final int id) {
		Assert.notNull(id);

		return this.articleRepository.findOne(id);
	}

	public Article save(final Article article, final Integer varId) {
		Assert.notNull(article);
		Assert.notNull(this.actorService.findByPrincipal());
		final Article saved = this.articleRepository.save(article);

		final Newspaper newspaper = this.newspaperService.findOne(varId);
		Assert.notNull(newspaper);
		newspaper.getArticles().add(saved);
		this.newspaperService.saveInternal(newspaper);

		return saved;
	}

	public void delete(final Article article, final Integer varId) {
		Assert.notNull(article);
		final Newspaper newspaper = this.newspaperService.findOne(varId);
		Assert.notNull(newspaper);
		newspaper.getArticles().remove(article);
		this.newspaperService.saveInternal(newspaper);

		this.articleRepository.delete(article);
	}

	//Other methods

	public Collection<Article> findByKeyword(final String word) {
		final Collection<Article> res = new ArrayList<Article>();

		for (final Article a : this.findAll())
			if (a.getTitle().contains(word) || a.getSummary().contains(word) || a.getBody().contains(word))
				res.add(a);
		return res;
	}

	//Ancillary methods

	public Double avgFollowupsPerArticle() {
		return this.articleRepository.avgFollowupsPerArticle();
	}

	public Double avgFollowupsPerArticleWeeks() {
		return this.articleRepository.avgFollowupsPerArticleWeeks();
	}
}
