
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Agent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AgentServiceTest extends AbstractTest {

	//Service under test

	@Autowired
	private AgentService	agentService;


	//Test template

	protected void Template(final String username, final String address, final String email, final String name, final String surname, final String phone, final String address2, final String email2, final String name2, final String surname2,
		final String phone2, final String username2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Creation

			final Agent agent = this.agentService.create();
			agent.setAddress(address);
			agent.setEmail(email);
			agent.setName(name);
			agent.setSurname(surname);
			agent.setPhone(phone);
			agent.getUserAccount().setUsername(username2);
			agent.getUserAccount().setPassword(username2);
			final Agent saved = this.agentService.save(agent);

			this.unauthenticate();
			this.authenticate(username2);

			//Listing

			Collection<Agent> cl = this.agentService.findAll();
			Assert.isTrue(cl.contains(saved));
			Assert.notNull(this.agentService.findOne(saved.getId()));

			//Edition

			saved.setAddress(address2);
			saved.setEmail(email2);
			saved.setName(name2);
			saved.setSurname(surname2);
			saved.setPhone(phone2);
			final Agent saved2 = this.agentService.save(saved);

			//Deletion

			this.agentService.delete(saved2);
			cl = this.agentService.findAll();
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
				"agent1", "testAddress", "testemail@alum.com", "testAgent", "testSurname", "+648456571", "editAddress", "editemail@alum.com", "editAgent", "editSurname", "+648456521", "user9", null

			},

			//Test #02: Attempt to save an user without proper credentials. Expected false.
			{
				"agent1", "testAddress", "testemail@alum.com", "testAgent", "testSurname", "+648456571", "editAddress", "editemail@alum.com", "editAgent", "editSurname", "+648456521", null, IllegalArgumentException.class

			},

			//Test #03: Attempt to create an user without email. Expected false.
			{
				"agent1", "testAddress", "", "testAgent", "testSurname", "+648456571", "editAddress", "editemail@alum.com", "editAgent", "editSurname", "+648456521", null, IllegalArgumentException.class

			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11], (Class<?>) testingData[i][12]);
	}
}
