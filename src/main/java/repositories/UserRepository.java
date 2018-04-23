
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Article;
import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	//The average and the standard deviation of newspapers created per user.
	@Query("select avg(u.newspapers.size), stddev(u.newspapers.size) from User u")
	Double[] avgstdNewspapersPerUser();

	//The average and the standard deviation of articles written by writer.
	@Query("select avg(u.articles.size), stddev(u.articles.size) from User u")
	Double[] avgstdArticlesPerWriter();

	//The ratio of users who have ever created a newspaper.
	@Query("select (select count(u) from User u where u.newspapers is not empty)*1.0/count(u) from User u")
	Double ratioUsersWithNewspaper();

	//The ratio of users who have ever written an article.
	@Query("select (select count(u) from User u where u.articles is not empty)*1.0/count(u) from User u")
	Double ratioUsersWithArticle();

	//The average and the standard deviation of the number of chirps per user.
	@Query("select avg(u.chirps.size), stddev(u.chirps.size) from User u")
	Double[] avgstdChirpsPerUser();

	//The ratio of users who have posted above 75% the average number of chirps	per user.
	@Query("select (select count(u) from User u where u.chirps.size *1.0 > (select avg(u.chirps.size)*1.75 from User u))*1.0/count(u) from User u")
	Double usersAboveAvgChirps();

	//The average ratio of private versus public newspapers per publisher.
	@Query("select (select count(n) from User u join u.newspapers n where n.isPrivate = true and n.publisher.id = ?1)*1.0/count(n) from User u join u.newspapers n where n.isPrivate = false and n.publisher.id = ?1")
	Double ratioPrivatePublicPerUser(int id);

	@Query("select a from User u join u.articles a where a.finalMode = true and a.writer.id = ?1")
	Collection<Article> articlesPublishedPerUser(int id);

}
