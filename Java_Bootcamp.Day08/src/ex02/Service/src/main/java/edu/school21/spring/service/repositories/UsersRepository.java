package edu.school21.spring.service.repositories;

import java.util.Optional;

import edu.school21.spring.service.models.User;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findByEmail(String email);
}
