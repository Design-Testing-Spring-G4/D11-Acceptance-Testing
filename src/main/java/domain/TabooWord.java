
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class TabooWord extends DomainEntity {

	//Attributes

	private String	word;


	//Getters

	@NotBlank
	public String getWord() {
		return this.word;
	}

	//Setters

	public void setWord(final String word) {
		this.word = word;
	}
}
