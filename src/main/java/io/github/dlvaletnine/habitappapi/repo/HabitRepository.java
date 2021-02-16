package io.github.dlvaletnine.habitappapi.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.github.dlvaletnine.habitappapi.models.Habit;

public interface HabitRepository extends CrudRepository<Habit, Integer> {
    public List<Habit> findByUid(Integer uid);
}
