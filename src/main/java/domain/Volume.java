
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Volume extends DomainEntity {

	//Attributes

	private String					title;
	private String					description;
	private Integer					year;

	//Relationships

	private User					publisher;
	private Collection<Newspaper>	newspapers;
	private Collection<Customer>	subscriptions;


	//Getters

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@Range(min = 1900, max = 2100)
	public Integer getYear() {
		return this.year;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getPublisher() {
		return this.publisher;
	}

	@NotNull
	@Valid
	@ManyToMany
	public Collection<Newspaper> getNewspapers() {
		return this.newspapers;
	}

	@NotNull
	@Valid
	@ManyToMany
	public Collection<Customer> getSubscriptions() {
		return this.subscriptions;
	}

	//Setters

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setYear(final Integer year) {
		this.year = year;
	}

	public void setPublisher(final User publisher) {
		this.publisher = publisher;
	}

	public void setNewspapers(final Collection<Newspaper> newspapers) {
		this.newspapers = newspapers;
	}

	public void setSubscriptions(final Collection<Customer> subscriptions) {
		this.subscriptions = subscriptions;
	}
}
