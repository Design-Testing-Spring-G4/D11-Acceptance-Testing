
package repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Advertisement;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {

	//The ratio of advertisements that have taboo words.
	@Query("select ?1*1.0/count(a) from Advertisement a")
	Double ratioAdsWithTabooWord(Double count);

	@Query("select a from Advertisement a where a.agent.id = ?1")
	Set<Advertisement> findWithAds(int id);

	@Query("select a from Advertisement a where a.agent.id != ?1")
	Set<Advertisement> findWithoutAds(int id);
}
