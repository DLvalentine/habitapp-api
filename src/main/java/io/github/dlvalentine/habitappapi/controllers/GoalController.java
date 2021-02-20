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

import io.github.dlvalentine.habitappapi.models.Goal;
import io.github.dlvalentine.habitappapi.repos.GoalRepository;
import io.github.dlvalentine.habitappapi.repos.UserRepository;
import io.github.dlvalentine.habitappapi.requests.DeleteGoalByIdRequest;
import io.github.dlvalentine.habitappapi.requests.GetGoalByIdRequest;
import io.github.dlvalentine.habitappapi.requests.GetGoalsForUserRequest;
import io.github.dlvalentine.habitappapi.responses.HttpResponse;

@RestController
public class GoalController {
    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    private String SUCCESSFUL_CREATE = "Success!";

    @GetMapping(path = "/goals", consumes = "application/json")
    public Iterable<Goal> getGoalsForUser(@RequestBody(required = true) GetGoalsForUserRequest req) {
        return goalRepository.findByUid(req.getUid());
    }

    @GetMapping(path = "/goal", consumes = "application/json")
    public Optional<Goal> getGoalById(@RequestBody(required = true) GetGoalByIdRequest req) {
        return goalRepository.findById(req.getId());
    }

    @DeleteMapping(path = "/goal/delete", consumes = "application/json")
    public HttpResponse deleteGoalById(@RequestBody(required = true) DeleteGoalByIdRequest req) {
        try {
            goalRepository.deleteById(req.getId());

            return new HttpResponse(
                SUCCESSFUL_CREATE,
                HttpStatus.OK.value()
            );
        } catch (Exception e) {
            return new HttpResponse(
                "Unable to delete Goal.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString()
            );
        }
    }

    @DeleteMapping(path = "/goals/delete", consumes = "application/json")
    public HttpResponse bulkDeleteHabitById(@RequestBody(required = true) List<DeleteGoalByIdRequest> reqs) {
        try {
            for(DeleteGoalByIdRequest req : reqs) {
                deleteGoalById(req);
            }

            return new HttpResponse(
                SUCCESSFUL_CREATE,
                HttpStatus.OK.value()
            );
        } catch (Exception e) {
            return new HttpResponse(
                "Unable to bulk delete Goals.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString()
            );
        }
    }

    // Implicitly creates goal for a given user, as UID is a required field.
    // TODO: Depending on front-end implementation, this could just return the 
    //       newly created goal object in order to get the ID for updates...
    //       or we could just update HttpResponse to include body.
    @PostMapping(path = "/goal/create", consumes = "application/json")
    public HttpResponse createGoal(@RequestBody(required = true) Goal goal) {
        try {
            if (!userRepository.findById(goal.getUid()).isPresent()) {
                return new HttpResponse(
                    "Unable to create Goal.",
                    HttpStatus.NOT_FOUND.value(),
                    "Provided User ID does not exist. Cannot create Goal."
                );
            }

            // TODO: Depending on front-end, may need to validate frequency string.

            Goal g = new Goal();

            g.setName(goal.getName());
            g.setDescription(goal.getDescription());
            g.setCreated(goal.getCreated());
            g.setUpdated(goal.getUpdated());
            g.setUid(goal.getUid());
            
            goalRepository.save(g);

            return new HttpResponse(
                SUCCESSFUL_CREATE,
                HttpStatus.OK.value()
            );
        } catch (Exception e) {
            return new HttpResponse(
                "Unable to create Goal.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString()
            );
        }
    }
}
