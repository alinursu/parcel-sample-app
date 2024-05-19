package ro.uaic.info.parcelexampleapp.repository;

import ro.uaic.info.parcelexampleapp.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
