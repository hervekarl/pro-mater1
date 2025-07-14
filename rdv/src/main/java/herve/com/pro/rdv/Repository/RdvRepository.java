package herve.com.pro.rdv.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import herve.com.pro.rdv.Modele.Rendezvous;

public interface RdvRepository extends JpaRepository<Rendezvous, Long> {

}
