
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Advertisement extends DomainEntity {

	//Attributes

	private String		title;
	private String		banner;
	private String		target;
	private CreditCard	creditCard;

	//Relationships

	private Agent		agent;
	private Newspaper	newspaper;


	//Getters

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	@URL
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBanner() {
		return this.banner;
	}

	@URL
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTarget() {
		return this.target;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Agent getAgent() {
		return this.agent;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Newspaper getNewspaper() {
		return this.newspaper;
	}

	//Setters

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setTarget(final String target) {
		this.target = target;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public void setAgent(final Agent agent) {
		this.agent = agent;
	}

	public void setNewspaper(final Newspaper newspaper) {
		this.newspaper = newspaper;
	}
}
