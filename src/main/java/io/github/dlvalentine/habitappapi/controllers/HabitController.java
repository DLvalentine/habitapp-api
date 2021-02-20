package io.github.dlvalentine.habitappapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.dlvalentine.habitappapi.models.Habit;
import io.github.dlvalentine.habitappapi.repos.GoalRepository;
import io.github.dlvalentine.habitappapi.repos.HabitRepository;
import io.github.dlvalentine.habitappapi.repos.UserRepository;
import io.github.dlvalentine.habitappapi.requests.DeleteHabitByIdRequest;
import io.github.dlvalentine.habitappapi.requests.GetHabitByGidRequest;
import io.github.dlvalentine.habitappapi.requests.GetHabitByIdRequest;
import io.github.dlvalentine.habitappapi.requests.GetHabitsForUserRequest;
import io.github.dlvalentine.habitappapi.responses.HttpResponse;

@RestController
public class HabitController {
    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoalRepository goalRepository;

    private String UNABLE_TO_CREATE = "Unable to create Habit.";
    private String SUCCESSFUL_CREATE = "Success!";

    @GetMapping(path = "/habits", consumes = "application/json")
    public Iterable<Habit> getHabitsForUser(@RequestBody(required = true) GetHabitsForUserRequest req) {
        return habitRepository.findByUid(req.getUid());
    }

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
                SUCCESSFUL_CREATE,
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

    @DeleteMapping(path = "/habits/delete", consumes = "application/json")
    public HttpResponse bulkDeleteHabitById(@RequestBody(required = true) List<DeleteHabitByIdRequest> reqs) {
        try {
            for(DeleteHabitByIdRequest req : reqs) {
                deleteHabitById(req);
            }

            return new HttpResponse(
                SUCCESSFUL_CREATE,
                HttpStatus.OK.value()
            );
        } catch (Exception e) {
            return new HttpResponse(
                "Unable to bulk delete Habits.",
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
                    UNABLE_TO_CREATE,
                    HttpStatus.NOT_FOUND.value(),
                    "Provided User ID does not exist. Cannot create Habit."
                );
            }

            if (habit.getGid() != null && !goalRepository.findById(habit.getGid()).isPresent()) {
                return new HttpResponse(
                    UNABLE_TO_CREATE,
                    HttpStatus.NOT_FOUND.value(),
                    "Provided Goal ID does not exist. Cannot create Habit."
                );
            }

            // TODO: Depending on front-end, may need to validate frequency string.

            Habit h = new Habit();

            h.setName(habit.getName());
            h.setDescription(habit.getDescription());
            h.setFrequency(habit.getFrequency());
            h.setCreated(habit.getCreated());
            h.setUpdated(habit.getUpdated());
            h.setUid(habit.getUid());
            h.setGid(habit.getGid());
            
            habitRepository.save(h);

            return new HttpResponse(
                SUCCESSFUL_CREATE,
                HttpStatus.OK.value()
            );
        } catch (Exception e) {
            return new HttpResponse(
                UNABLE_TO_CREATE,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString()
            );
        }
    }
}
