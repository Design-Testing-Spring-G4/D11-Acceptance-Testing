
package domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Agent extends Actor {

	//Relationships

	private Collection<Advertisement>	advertisements;


	//Getters

	@NotNull
	@Valid
	@OneToMany(mappedBy = "agent")
	public Collection<Advertisement> getAdvertisements() {
		return this.advertisements;
	}

	//Setters

	public void setAdvertisements(final Collection<Advertisement> advertisements) {
		this.advertisements = advertisements;
	}
}
