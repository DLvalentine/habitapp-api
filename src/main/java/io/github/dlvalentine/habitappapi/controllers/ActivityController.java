package io.github.dlvalentine.habitappapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.dlvalentine.habitappapi.models.HabitActivity;
import io.github.dlvalentine.habitappapi.repos.HabitActivityRepository;
import io.github.dlvalentine.habitappapi.repos.HabitRepository;
import io.github.dlvalentine.habitappapi.repos.UserRepository;
import io.github.dlvalentine.habitappapi.requests.DeleteHabitActivityByIdRequest;
import io.github.dlvalentine.habitappapi.requests.GetHabitActivityByHabitIdRequest;
import io.github.dlvalentine.habitappapi.responses.HttpResponse;

@RestController
public class ActivityController {
    @Autowired
    private HabitActivityRepository habitActivityRepository;

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private UserRepository userRepository;

    private String UNABLE_TO_CREATE = "Unable to record activity.";
    private String SUCCESSFUL_CREATE = "Success!";

    @GetMapping(path = "/habit/activity", consumes = "application/json")
    public Iterable<HabitActivity> getHabitActivityByHabitId(@RequestBody(required = true) GetHabitActivityByHabitIdRequest req) {
        return habitActivityRepository.findByHid(req.getHid());
    }

    @DeleteMapping(path = "/habit/activity/delete", consumes = "application/json")
    public HttpResponse deleteHabitActivityById(@RequestBody(required = true) DeleteHabitActivityByIdRequest req) {
        try {
            habitActivityRepository.deleteById(req.getId());

            return new HttpResponse(
                SUCCESSFUL_CREATE,
                HttpStatus.OK.value()
            );
        } catch (Exception e) {
            return new HttpResponse(
                "Unable to delete Activity",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString()
            );
        }
    }

    @PostMapping(path = "/habit/activity/record", consumes = "application/json")
    public HttpResponse recordHabitActivity(@RequestBody(required = true) HabitActivity activity) {
        try {
            if(!userRepository.findById(activity.getUid()).isPresent()) {
                return new HttpResponse(
                    UNABLE_TO_CREATE,
                    HttpStatus.NOT_FOUND.value(),
                    "Provided User ID does not exist. Cannot record Activity."
                );
            }

            if(activity.getHid() != null && !habitRepository.findById(activity.getHid()).isPresent()) {
                return new HttpResponse(
                    UNABLE_TO_CREATE,
                    HttpStatus.NOT_FOUND.value(),
                    "Provided Habit ID does not exist. Cannot record activity for null Habit."
                );
            }

            HabitActivity ha = new HabitActivity();

            ha.setComment(activity.getComment());
            ha.setCreated(activity.getCreated());
            ha.setHid(activity.getHid());
            ha.setUid(activity.getUid());

            habitActivityRepository.save(ha);

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
