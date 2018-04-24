
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Volume;

@Repository
public interface VolumeRepository extends JpaRepository<Volume, Integer> {

	//The average number of newspapers per volume.
	@Query("select avg(v.newspapers.size) from Volume v")
	Double avgNewspapersPerVolume();

	//The ratio of subscriptions to volumes versus subscriptions to newspapers.
	@Query("select (select sum(v.subscriptions.size) from Volume v)*1.0/sum(n.customers.size) from Newspaper n")
	Double ratioSubscriptionsVolumesNewspapers();
}
