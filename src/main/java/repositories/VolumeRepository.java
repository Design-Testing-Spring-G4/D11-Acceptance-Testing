
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
}
