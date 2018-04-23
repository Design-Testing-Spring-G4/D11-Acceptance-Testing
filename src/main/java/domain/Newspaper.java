
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "isPrivate, publicationDate")
})
public class Newspaper extends DomainEntity {

	//Attributes

	private String					title;
	private Date					publicationDate;
	private String					description;
	private String					picture;
	private boolean					isPrivate;

	//Relationships

	private User					publisher;
	private Collection<Customer>	customers;
	private Collection<Article>		articles;


	//Getters

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getPublicationDate() {
		return this.publicationDate;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@URL
	@NotNull
	public String getPicture() {
		return this.picture;
	}

	public boolean getIsPrivate() {
		return this.isPrivate;
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
	public Collection<Customer> getCustomers() {
		return this.customers;
	}

	@NotNull
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Article> getArticles() {
		return this.articles;
	}

	//Setters

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setPublicationDate(final Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public void setIsPrivate(final boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public void setPrivate(final boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public void setPublisher(final User publisher) {
		this.publisher = publisher;
	}

	public void setCustomers(final Collection<Customer> customers) {
		this.customers = customers;
	}

	public void setArticles(final Collection<Article> articles) {
		this.articles = articles;
	}
}
