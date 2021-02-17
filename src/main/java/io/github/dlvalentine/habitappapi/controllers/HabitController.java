package io.github.dlvalentine.habitappapi.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.dlvalentine.habitappapi.models.Habit;
import io.github.dlvalentine.habitappapi.repos.HabitRepository;
import io.github.dlvalentine.habitappapi.repos.UserRepository;
import io.github.dlvalentine.habitappapi.requests.DeleteHabitByIdRequest;
import io.github.dlvalentine.habitappapi.requests.GetHabitByGidRequest;
import io.github.dlvalentine.habitappapi.requests.GetHabitByIdRequest;
import io.github.dlvalentine.habitappapi.requests.GetHabitsForUserRequest;
import io.github.dlvalentine.habitappapi.responses.HttpResponse;

// TODO: bulk methods for delete (accept: series of ids, accept: entire user habit lib)

@RestController
public class HabitController {
    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/habits", consumes = "application/json")
    public Iterable<Habit> getHabitsForUser(@RequestBody(required = true) GetHabitsForUserRequest req) {
        return habitRepository.findByUid(req.getUid());
    }

    // TODO: Ideally, we should check the UID too to make sure only the habit for the profile/user 
    //       currently in use is returned
    // TODO: ^ try to use JPA for this instead of native query
    @GetMapping(path = "/habit", consumes = "application/json")
    public Optional<Habit> getHabitById(@RequestBody(required =  true) GetHabitByIdRequest req) {
        return habitRepository.findById(req.getId());
    }

    @GetMapping(path = "/habit/byGoal", consumes = "application/json")
    public Iterable<Habit> getHabitByGid(@RequestBody(required =  true) GetHabitByGidRequest req) {
        return habitRepository.findByGid(req.getGid());
    }

    @DeleteMapping(path = "/habit/delete", consumes = "application/json")
    public HttpResponse deleteHabitById(@RequestBody(required = true) DeleteHabitByIdRequest req) {
        try {
            habitRepository.deleteById(req.getId());

            return new HttpResponse(
                "Success",
                HttpStatus.OK.value()
            );
        } catch (Exception e) {
            return new HttpResponse(
                "Unable to delete Habit.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString()
            );
        }
    }

    // Implicitly creates habit for a given user, as UID is a required field.
    // TODO: Depending on front-end implementation, this could just return the 
    //       newly created habit object in order to get the ID for updates...
    //       or we could just update HttpResponse to include body.
    @PostMapping(path = "/habit/create", consumes = "application/json")
    public HttpResponse createHabit(@RequestBody(required = true) Habit habit) {
        try {
            if (!userRepository.findById(habit.getUid()).isPresent()) {
                return new HttpResponse(
                    "Unable to create Habit.",
                    HttpStatus.NOT_FOUND.value(),
                    "Provided User ID does not exist. Cannot create Habit."
                );
            }

            // TODO: Need to ensure that Goal (by GID) exists if passed

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