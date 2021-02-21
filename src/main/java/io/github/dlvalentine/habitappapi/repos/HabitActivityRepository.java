package io.github.dlvalentine.habitappapi.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.github.dlvalentine.habitappapi.models.HabitActivity;

public interface HabitActivityRepository extends CrudRepository<HabitActivity, Integer> {
    public List<HabitActivity> findByHid(Integer hid);
}
