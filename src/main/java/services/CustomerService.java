
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.UserAccount;
import domain.Customer;
import domain.Folder;
import domain.Subscription;

@Service
@Transactional
public class CustomerService {

	//Managed repository ---------------------------------

	@Autowired
	private CustomerRepository	customerRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;


	//Simple CRUD Methods --------------------------------

	public Customer create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.CUSTOMER);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));

		final Customer customer = new Customer();
		customer.setUserAccount(account);
		customer.setSubscriptions(new ArrayList<Subscription>());
		customer.setFolders(new ArrayList<Folder>());

		return customer;
	}

	public Collection<Customer> findAll() {
		return this.customerRepository.findAll();
	}

	public Customer findOne(final int id) {
		Assert.notNull(id);

		return this.customerRepository.findOne(id);
	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);

		final Customer saved2;
		//Assertion that the user modifying this user has the correct privilege.
		if (customer.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == customer.getId());
			saved2 = this.customerRepository.save(customer);
		} else {
			final Customer saved = this.customerRepository.save(customer);
			saved.setFolders(this.folderService.generateDefaultFolders(saved));
			saved2 = this.customerRepository.save(saved);
		}

		return saved2;
	}

	public void delete(final Customer customer) {
		Assert.notNull(customer);

		//Assertion that the user deleting this customer has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == customer.getId());

		this.customerRepository.delete(customer);
	}
}
