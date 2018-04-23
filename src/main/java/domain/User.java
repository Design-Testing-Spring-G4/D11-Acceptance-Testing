
package domain;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class User extends Actor {

	//Relationships

	private Collection<User>		followers;
	private Collection<Chirp>		chirps;
	private Collection<Newspaper>	newspapers;
	private Collection<Article>		articles;


	//Getters

	@ElementCollection
	@NotNull
	@Valid
	public Collection<User> getFollowers() {
		return this.followers;
	}

	@NotNull
	@Valid
	@OneToMany
	public Collection<Chirp> getChirps() {
		return this.chirps;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "publisher")
	public Collection<Newspaper> getNewspapers() {
		return this.newspapers;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "writer")
	public Collection<Article> getArticles() {
		return this.articles;
	}

	//Setters

	public void setFollowers(final Collection<User> followers) {
		this.followers = followers;
	}

	public void setChirps(final Collection<Chirp> chirps) {
		this.chirps = chirps;
	}

	public void setNewspapers(final Collection<Newspaper> newspapers) {
		this.newspapers = newspapers;
	}

	public void setArticles(final Collection<Article> articles) {
		this.articles = articles;
	}
}
