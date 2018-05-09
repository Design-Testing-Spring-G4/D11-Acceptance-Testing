
package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Newspaper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class NewspaperServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private NewspaperService	newspaperService;


	//Test template

	protected void Template(final String username, final String title, final Date publicationDate, final String description, final String picture, final Boolean isPrivate, final String title2, final Date publicationDate2, final String description2,
		final String picture2, final Boolean isPrivate2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation

			final Newspaper newspaper = this.newspaperService.create();
			newspaper.setTitle(title);
			newspaper.setPublicationDate(publicationDate);
			newspaper.setDescription(description);
			newspaper.setPicture(picture);
			newspaper.setPrivate(isPrivate);

			final Newspaper saved = this.newspaperService.save(newspaper);

			//Listing

			Collection<Newspaper> cl = this.newspaperService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.newspaperService.findOne(saved.getId()));

			//Edition

			saved.setTitle(title2);
			saved.setPublicationDate(publicationDate2);
			saved.setDescription(description2);
			saved.setPicture(picture2);
			saved.setPrivate(isPrivate2);

			final Newspaper saved2 = this.newspaperService.save(saved);

			//Deletion

			this.newspaperService.delete(saved2);
			cl = this.newspaperService.findAll();
			Assert.isTrue(!cl.contains(saved));

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Driver for multiple tests under the same template.

	@Test
	public void Driver() {

		final Object testingData[][] = {

			//Test #01: Correct execution of test. Expected true.
			{
				"user1", "testNewspaper", new Date(1539158400000L), "testDescription", "https://tinyurl.com/adventure-meetup", false, "editNewspaper", new Date(1539158400000L), "editDescription", "http://eskipaper.com/images/savannah-5.jpg", true, null
			},

			//Test #02:  Attempt to execute the test by anonymous user. Expected false.
			{
				null, "testNewspaper", new Date(1539158400000L), "testDescription", "https://tinyurl.com/adventure-meetup", false, "editNewspaper", new Date(1539158400000L), "editDescription", "http://eskipaper.com/images/savannah-5.jpg", true,
				IllegalArgumentException.class

			},

			//Test #03: Attempt to execute the test by unauthorized user. Expected false.			{
			{
				"admin", "testNewspaper", new Date(1539158400000L), "testDescription", "https://tinyurl.com/adventure-meetup", false, "editNewspaper", new Date(1539158400000L), "editDescription", "http://eskipaper.com/images/savannah-5.jpg", true,
				ClassCastException.class

			},

			//Test #04: Attempt to create a newspaper with blank text. Expected false.

			{
				"user1", "", new Date(1539158400000L), "", "https://tinyurl.com/adventure-meetup", false, "editNewspaper", new Date(1539158400000L), "editDescription", "http://eskipaper.com/images/savannah-5.jpg", true, ConstraintViolationException.class

			},

			//Test #05: Attempt to edit a newspaper with null values. Expected false.
			{

				"user1", "testNewspaper", new Date(1539158400000L), "testDescription", "https://tinyurl.com/adventure-meetup", false, null, new Date(1539158400000L), null, "http://eskipaper.com/images/savannah-5.jpg", true,
				ConstraintViolationException.class

			},

			//Test #06: Attempt to create a newspaper with a null date. Expected false.
			{

				"user1", "testNewspaper", null, "testDescription", "https://tinyurl.com/adventure-meetup", false, null, new Date(1539158400000L), null, "http://eskipaper.com/images/savannah-5.jpg", true, ConstraintViolationException.class
			},

			//Test #07: Attempt to create a newspaper with an invalid picture. Expected false.
			{
				"user1", "testNewspaper", new Date(1539158400000L), "testDescription", "https://tinyurl.com/adventure-meetup", false, "editNewspaper", new Date(1539158400000L), "editDescription", "invalidPicture", true, ConstraintViolationException.class

			}

		//Test #08: Expected false.
		//			{
		//
		//			},
		//
		//			//Test #09: Expected false.
		//			{
		//
		//			},
		//
		//			//Test #10: Expected false.
		//			{
		//
		//			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Boolean) testingData[i][5], (String) testingData[i][6], (Date) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (Boolean) testingData[i][10], (Class<?>) testingData[i][11]);
	}
}
