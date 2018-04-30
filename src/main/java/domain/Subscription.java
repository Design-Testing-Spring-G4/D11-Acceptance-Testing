
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Subscription extends DomainEntity {

	//Attributes

	private CreditCard	creditCard;

	//Relationships

	private Customer	customer;
	private Volume		volume;
	private Newspaper	newspaper;


	//Getters

	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	@Valid
	@ManyToOne(optional = true)
	public Volume getVolume() {
		return this.volume;
	}

	@Valid
	@ManyToOne(optional = true)
	public Newspaper getNewspaper() {
		return this.newspaper;
	}

	//Setters

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public void setVolume(final Volume volume) {
		this.volume = volume;
	}

	public void setNewspaper(final Newspaper newspaper) {
		this.newspaper = newspaper;
	}
}
