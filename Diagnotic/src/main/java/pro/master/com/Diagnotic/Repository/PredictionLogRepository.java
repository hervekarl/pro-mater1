package pro.master.com.Diagnotic.Repository;


import pro.master.com.Diagnotic.Model.PredictionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionLogRepository extends JpaRepository<PredictionLog, Long> {
}
