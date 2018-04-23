
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "finalMode")
})
public class Article extends DomainEntity {

	//Attributes

	private String					title;
	private Date					moment;
	private String					summary;
	private String					body;
	private Collection<String>		pictures;
	private boolean					finalMode;

	//Relationships

	private User					writer;
	private Collection<Followup>	followups;


	//Getters

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMoment() {
		return this.moment;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	@ElementCollection
	@NotNull
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public boolean isFinalMode() {
		return this.finalMode;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getWriter() {
		return this.writer;
	}

	@NotNull
	@Valid
	@OneToMany
	public Collection<Followup> getFollowups() {
		return this.followups;
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

	public void setBody(final String body) {
		this.body = body;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

	public void setWriter(final User writer) {
		this.writer = writer;
	}

	public void setFollowups(final Collection<Followup> followups) {
		this.followups = followups;
	}
}
