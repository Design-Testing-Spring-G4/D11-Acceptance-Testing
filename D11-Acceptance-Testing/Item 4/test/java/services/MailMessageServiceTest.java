
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
import domain.MailMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MailMessageServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private MailMessageService	mailMessageService;

	@Autowired
	private FolderService		folderService;

	@Autowired
	private ActorService		actorService;


	//Test template

	protected void Template(final String username, final String subject, final String body, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation

			final MailMessage message = this.mailMessageService.create();
			message.setSubject(subject);
			message.setBody(body);
			message.setReceiver(this.actorService.findOne(this.getEntityId("admin1")));
			message.setFolder(this.folderService.findOne(this.getEntityId("inbox4")));

			final MailMessage saved = this.mailMessageService.save(message);

			//Listing

			final Collection<MailMessage> cl = this.mailMessageService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.mailMessageService.findOne(saved.getId()));

			//Deletion

			this.mailMessageService.delete(saved);

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
				"user1", "testMail", "testSubject", null
			},

			//Test #02:  Attempt to execute the test by anonymous user. Expected false.
			{
				null, "testMail", "testSubject", IllegalArgumentException.class
			},

			//Test #03: Attempt to create a message with null subject. Expected false.
			{
				"user1", null, "testSubject", ConstraintViolationException.class
			},

			//Test #04: Attempt to create a message with null body. Expected false.
			{
				"user1", "testMail", null, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

}
