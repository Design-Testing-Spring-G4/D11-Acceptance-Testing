
package domain;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Customer extends Actor {

	//Attributes

	private Collection<CreditCard>	creditCards;


	//Getters

	@ElementCollection
	@NotNull
	@Valid
	public Collection<CreditCard> getCreditCards() {
		return this.creditCards;
	}

	//Setters

	public void setCreditCards(final Collection<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}
}
