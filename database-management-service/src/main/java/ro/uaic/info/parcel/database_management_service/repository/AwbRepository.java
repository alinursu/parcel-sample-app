package ro.uaic.info.parcel.database_management_service.repository;

import org.springframework.data.repository.CrudRepository;
import ro.uaic.info.parcel.database_management_service.domain.Awb;

public interface AwbRepository extends CrudRepository<Awb, Long> {
    Awb findByUniqueNumber(String uniqueNumber);
}
