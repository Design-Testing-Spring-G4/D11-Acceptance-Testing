
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

	//The ratio of subscriptions to volumes versus subscriptions to newspapers.
	@Query("select (select count(s) from Subscription s where s.volume is not null)*1.0/count(s) from Subscription s where s.volume is null")
	Double ratioSubscriptionsVolumesNewspapers();
}
