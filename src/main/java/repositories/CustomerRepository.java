
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	//The ratio of subscribers per private newspaper versus the total number of customers.
	@Query("select (select avg(n.customers.size)from Newspaper n)*1.0/count(c) from Customer c")
	Double ratioCustomerSubscriber();
}
