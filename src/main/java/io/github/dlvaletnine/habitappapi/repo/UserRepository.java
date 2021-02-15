package io.github.dlvaletnine.habitappapi.repo;

import org.springframework.data.repository.CrudRepository;

import io.github.dlvaletnine.habitappapi.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {}
