
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Advertisement;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {

	//The ratio of advertisements that have taboo words.
	@Query("select ?1*1.0/count(a) from Advertisement a")
	Double ratioAdsWithTabooWord(Integer count);
}
