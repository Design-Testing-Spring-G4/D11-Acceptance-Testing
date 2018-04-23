
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Article;
import domain.Newspaper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ArticleServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private ArticleService		articleService;

	@Autowired
	private NewspaperService	newspaperService;


	//Test template

	protected void Template(final String username, final String title, final String summary, final String body, final Collection<String> pictures, final Boolean finalMode, final String title2, final String summary2, final String body2,
		final Collection<String> pictures2, final Boolean finalMode2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			final Newspaper n = this.newspaperService.findOne(this.getEntityId("newspaper1"));
			//Creation

			final Article article = this.articleService.create(n.getId());
			article.setTitle(title);
			article.setSummary(summary);
			article.setBody(body);
			article.setPictures(pictures);
			article.setFinalMode(finalMode);

			final Article saved = this.articleService.save(article, n.getId());

			//Listing

			Collection<Article> cl = this.articleService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.articleService.findOne(saved.getId()));

			//Edition

			saved.setTitle(title2);
			saved.setSummary(summary2);
			saved.setBody(body2);
			saved.setPictures(pictures2);
			saved.setFinalMode(finalMode);

			final Article saved2 = this.articleService.save(saved, n.getId());

			//Deletion

			this.articleService.delete(saved2, n.getId());
			cl = this.articleService.findAll();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}


	//Driver for multiple tests under the same template.

	Collection<String>	pictures	= new ArrayList<String>();


	@Test
	public void Driver() {

		// final Boolean finalMode2, final Class<?> expected

		final Object testingData[][] = {

			//Test #01: Correct execution of test. Expected true.
			{
				"user1", "testArticle", "testSummary", "testBody", this.pictures, false, "editArticle", "editSummary", "editBody", this.pictures, true, null

			},

			//Test #02:  Attempt to execute the test by anonymous user. Expected false.
			{
				null, "testArticle", "testSummary", "testBody", this.pictures, false, "editArticle", "editSummary", "editBody", this.pictures, true, IllegalArgumentException.class
			},

			//Test #03: Attempt to execute the test by unauthorized user. Expected false.
			{
				"admin", "testArticle", "testSummary", "testBody", this.pictures, false, "editArticle", "editSummary", "editBody", this.pictures, true, ClassCastException.class
			},

			//Test #04: Attempt to create an article with blank test. Expected false.
			{
				"user1", "", "", "", this.pictures, false, "editArticle", "editSummary", "editBody", this.pictures, true, ConstraintViolationException.class
			},

			//Test #05: Creation of an article on final mode and posterior attempt to edit it. Expected false.
			{
				"user1", "testArticle", "testSummary", "testBody", this.pictures, true, "editArticle", "editSummary", "editBody", this.pictures, false, ConstraintViolationException.class

			},
			//Test #06: Attempt to edit a rendezvous with null values. Expected false.
			{
				"user1", "testArticle", "testSummary", "testBody", this.pictures, false, null, null, null, this.pictures, true, ConstraintViolationException.class

			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], this.pictures, (Boolean) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], this.pictures, (Boolean) testingData[i][10], (Class<?>) testingData[i][11]);
	}
}
