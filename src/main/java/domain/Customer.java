
package domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Customer extends Actor {

	//Relationships

	private Collection<Subscription>	subscriptions;


	//Getters

	@NotNull
	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Subscription> getSubscriptions() {
		return this.subscriptions;
	}

	//Setters

	public void setSubscriptions(final Collection<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}
}
