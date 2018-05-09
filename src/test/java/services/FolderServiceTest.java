
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
import domain.Folder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FolderServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


	//Test template

	protected void Template(final String username, final String name, final Boolean system, final String name2, final Boolean system2, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			//Creation

			final Folder folder = this.folderService.create(this.actorService.findByPrincipal());
			folder.setName(name);
			folder.setSystem(system);

			final Folder saved = this.folderService.save(folder);

			//Listing

			Collection<Folder> cl = this.folderService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.folderService.findOne(saved.getId()));

			//Edition

			saved.setName(name2);
			saved.setSystem(system2);

			final Folder saved2 = this.folderService.save(saved);

			//Deletion

			this.folderService.delete(saved2);
			cl = this.folderService.findAll();
			Assert.isTrue(!cl.contains(saved2));

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void Driver() {

		final Object testingData[][] = {

			//Test #01: Correct execution of test. Expected true.
			{
				"user1", "testFolder", false, "editFolder", false, null

			},

			//Test #02:  Attempt to execute the test by anonymous user. Expected false.
			{
				null, "testFolder", false, "editFolder", false, IllegalArgumentException.class
			},

			//Test #03: Attempt to create a folder with blank name. Expected false.
			{
				"user1", "", false, "editFolder", false, ConstraintViolationException.class
			},

			//Test #04: Attempt to create a system folder and try to edit or delete it with blank body. Expected false.
			{
				"admin1", "testFolder", true, "editFolder", true, IllegalArgumentException.class

			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (Boolean) testingData[i][2], (String) testingData[i][3], (Boolean) testingData[i][4], (Class<?>) testingData[i][5]);
	}

}
