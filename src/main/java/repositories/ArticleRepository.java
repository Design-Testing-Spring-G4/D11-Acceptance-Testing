
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	//The average number of follow-ups per article.
	@Query("select avg(a.followups.size) from Article a")
	Double avgFollowupsPerArticle();

	//The average number of follow-ups per article up to x weeks after the corresponding newspaper's been published.
	@Query("select avg(a.followups.size) from Article a")
	Double avgFollowupsPerArticleWeeks();
}
