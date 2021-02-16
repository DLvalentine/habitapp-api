package io.github.dlvaletnine.habitappapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.dlvaletnine.habitappapi.models.Habit;
import io.github.dlvaletnine.habitappapi.models.HttpResponse;
import io.github.dlvaletnine.habitappapi.repo.HabitRepository;
import io.github.dlvaletnine.habitappapi.repo.UserRepository;

@RestController
public class HabitController {
    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/habits")
    public Iterable<Habit> getHabitsForUser(@RequestParam(required = true) Integer uid) {
        return habitRepository.findByUid(uid);
    }

    // Implicitly creates habit for a given user, as UID is a required field.
    // TODO: Depending on front-end implementation, this could just return the 
    //       newly created habit object in order to get the ID for updates...
    //       or we could just update HttpResponse to include body.
    @PostMapping(path = "/habits/create", consumes = "application/json")
    public HttpResponse createHabit(@RequestBody(required = true) Habit habit) {
        try {
            if (!userRepository.findById(habit.getUid()).isPresent()) {
                return new HttpResponse(
                    "Unable to create Habit.",
                    HttpStatus.NOT_FOUND.value(),
                    "Provided User ID does not exist. Cannot create Habit."
                );
            }

            Habit h = new Habit();

            h.setName(habit.getName());
            h.setDescription(habit.getDescription());
            h.setFrequency(habit.getFrequency());
            h.setCreated(habit.getCreated());
            h.setUpdated(habit.getUpdated());
            h.setUid(habit.getUid());
            
            habitRepository.save(h);

            return new HttpResponse(
                "Success",
                HttpStatus.OK.value()
            );
        } catch (Exception e) {
            return new HttpResponse(
                "Unable to create Habit.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString()
            );
        }
    }
}
