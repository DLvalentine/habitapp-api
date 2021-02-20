package io.github.dlvalentine.habitappapi.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.github.dlvalentine.habitappapi.models.Goal;

public interface GoalRepository extends CrudRepository<Goal, Integer> {
    public List<Goal> findByUid(Integer uid);
}
