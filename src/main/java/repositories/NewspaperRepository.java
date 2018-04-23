
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Newspaper;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Integer> {

	//The average and the standard deviation of articles per newspaper
	@Query("select avg(n.articles.size), stddev(n.articles.size) from Newspaper n")
	Double[] avgstdArticlesPerNewspaper();

	//The newspapers that have at least 10% more articles than the average
	@Query("select n from Newspaper n where n.articles.size >= (select avg(n.articles.size)*1.1 from Newspaper n)")
	Collection<Newspaper> newspapersAboveAvg();

	//The newspapers that have at least 10% fewer articles than the average
	@Query("select n from Newspaper n where n.articles.size <= (select avg(n.articles.size)*1.1 from Newspaper n)")
	Collection<Newspaper> newspapersUnderAvg();

	//The ratio of public versus private newspapers.
	@Query("select (select count(n) from Newspaper n where n.isPrivate = false)*1.0/count(n) from Newspaper n where n.isPrivate = true")
	Double ratioPublicNewspapers();

	//The average number of articles per private newspapers
	@Query("select avg(n.articles.size) from Newspaper n where n.isPrivate = true")
	Double avgArticlesPerPrivateNewspaper();

	//The average number of articles per public newspapers
	@Query("select avg(n.articles.size) from Newspaper n where n.isPrivate = false")
	Double avgArticlesPerPublicNewspaper();

	@Query("select n from Newspaper n join n.articles a where a.finalMode = true and n.publicationDate <= ?1")
	Collection<Newspaper> newspapersForToPublish(Date date);

	@Query("select n from Newspaper n where n.publicationDate > ?1")
	Collection<Newspaper> newspapersForNotToPublish(Date date);

	@Query("select n from Newspaper n join n.articles a where a.id = ?1")
	Newspaper newspapersWhoContainsArticle(int id);
}
