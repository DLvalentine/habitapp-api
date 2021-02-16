package io.github.dlvaletnine.habitappapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.dlvaletnine.habitappapi.models.Habit;
import io.github.dlvaletnine.habitappapi.repo.HabitRepository;

@RestController
public class HabitController {
    @Autowired
    private HabitRepository habitRepository;

    @GetMapping("/habits")
    public Iterable<Habit> getHabitsForUser(@RequestParam(required = true) Integer uid) {
        return habitRepository.findByUid(uid);
    }
}
