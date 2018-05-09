
package services;

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
import domain.Advertisement;
import domain.CreditCard;
import domain.Newspaper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdvertisementServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private AdvertisementService	advertisementService;

	@Autowired
	private NewspaperService		newspaperService;


	//Test template

	protected void Template(final String username, final String title, final String banner, final String target, final CreditCard creditcard, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			final Newspaper n = this.newspaperService.findOne(this.getEntityId("newspaper1"));

			//Creation

			final Advertisement advertisement = this.advertisementService.create();
			advertisement.setTitle(title);
			advertisement.setBanner(banner);
			advertisement.setTarget(target);
			advertisement.setCreditCard(creditcard);
			advertisement.setNewspaper(n);

			final Advertisement saved = this.advertisementService.save(advertisement);

			//Listing

			Collection<Advertisement> cl = this.advertisementService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.advertisementService.findOne(saved.getId()));

			//Deletion

			this.advertisementService.delete(saved);
			cl = this.advertisementService.findAll();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void Driver() {

		//Creating a creditcard

		final CreditCard creditcard = new CreditCard();
		creditcard.setHolder("MasterCard");
		creditcard.setBrand("María Carcaño Fuentes");
		creditcard.setNumber("5564157826282522");
		creditcard.setExpMonth(10);
		creditcard.setExpYear(2020);
		creditcard.setCvv(150);

		final Object testingData[][] = {

			//Test #01: Correct execution of test. Expected true.
			{
				"agent1", "testAdvertisement", "https://tinyurl.com/adventure-meetup", "http://eskipaper.com/images/savannah-5.jpg", creditcard, null

			},

			//Test #02:  Attempt to execute the test by anonymous user. Expected false.
			{
				null, "testAdvertisement", "https://tinyurl.com/adventure-meetup", "http://eskipaper.com/images/savannah-5.jpg", creditcard, IllegalArgumentException.class
			},

			//Test #03: Attempt to execute the test by unauthorized user. Expected false.
			{
				"admin", "testAdvertisement", "https://tinyurl.com/adventure-meetup", "http://eskipaper.com/images/savannah-5.jpg", creditcard, ClassCastException.class
			},

			//Test #04: Attempt to create an advertisement with blank title. Expected false.
			{
				"agent1", "", "https://tinyurl.com/adventure-meetup", "http://eskipaper.com/images/savannah-5.jpg", creditcard, ConstraintViolationException.class
			},

			//Test #05: Attempt to edit an advertisement with blank banner. Expected false.
			{
				"agent1", "testAdvertisement", "", "http://eskipaper.com/images/savannah-5.jpg", creditcard, ConstraintViolationException.class

			},
			//Test #06: Attempt to edit an advertisement with blank target. Expected false.
			{
				"agent1", "testAdvertisement", "https://tinyurl.com/adventure-meetup", "", creditcard, ConstraintViolationException.class

			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (CreditCard) testingData[i][4], (Class<?>) testingData[i][5]);
	}
}
