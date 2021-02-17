package io.github.dlvalentine.habitappapi.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.github.dlvalentine.habitappapi.models.Habit;

public interface HabitRepository extends CrudRepository<Habit, Integer> {
    public List<Habit> findByUid(Integer uid);
    public List<Habit> findByGid(Integer gid);
}
