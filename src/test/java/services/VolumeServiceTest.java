
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
import domain.Volume;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class VolumeServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private VolumeService	volumeService;


	//Test template

	protected void Template(final String username, final String title, final String description, final Integer year, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation

			final Volume volume = this.volumeService.create();
			volume.setTitle(title);
			volume.setDescription(description);
			volume.setYear(year);

			final Volume saved = this.volumeService.save(volume);

			//Listing

			Collection<Volume> cl = this.volumeService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.volumeService.findOne(saved.getId()));

			//Edition

			//Deletion

			this.volumeService.delete(saved);
			cl = this.volumeService.findAll();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void Driver() {

		// final Boolean finalMode2, final Class<?> expected

		final Object testingData[][] = {

			//Test #01: Correct execution of test. Expected true.
			{
				"user1", "testVolume", "testDescription", 2010, null

			},

			//Test #02:  Attempt to execute the test by anonymous user. Expected false.
			{
				null, "testVolume", "testDescription", 2010, IllegalArgumentException.class
			},

			//Test #03: Attempt to execute the test by unauthorized user. Expected false.
			{
				"admin", "testVolume", "testDescription", 2010, ClassCastException.class
			},

			//Test #04: Attempt to create an volume with blank title. Expected false.
			{
				"user1", "", "testDescription", 2010, ConstraintViolationException.class
			},

			//Test #05: Attempt to create an volume with blank description. Expected false.
			{
				"user1", "testVolume", "", 2010, ConstraintViolationException.class

			},
			//Test #06: Attempt to create an volume with invalid year. Expected false.
			{
				"user1", "testVolume", "testDescription", 1000, ConstraintViolationException.class

			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Class<?>) testingData[i][4]);
	}
}
