package io.github.dlvalentine.habitappapi.repos;

import org.springframework.data.repository.CrudRepository;

import io.github.dlvalentine.habitappapi.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {}
