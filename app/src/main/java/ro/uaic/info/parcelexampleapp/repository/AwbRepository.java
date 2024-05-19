package ro.uaic.info.parcelexampleapp.repository;

import ro.uaic.info.parcelexampleapp.domain.Awb;
import org.springframework.data.repository.CrudRepository;

public interface AwbRepository extends CrudRepository<Awb, Long> {
    Awb findByUniqueNumber(String uniqueNumber);
}
