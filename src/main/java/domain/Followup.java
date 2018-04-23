
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Followup extends DomainEntity {

	//Attributes

	private String				title;
	private Date				moment;
	private String				summary;
	private String				text;
	private Collection<String>	pictures;


	//Getters

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	@ElementCollection
	@NotNull
	public Collection<String> getPictures() {
		return this.pictures;
	}

	//Setters

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}
}
