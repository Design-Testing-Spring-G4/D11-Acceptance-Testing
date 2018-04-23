
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Followup;

@Repository
public interface FollowupRepository extends JpaRepository<Followup, Integer> {

}
