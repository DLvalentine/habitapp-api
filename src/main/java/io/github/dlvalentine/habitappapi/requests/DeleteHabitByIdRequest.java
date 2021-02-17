package io.github.dlvalentine.habitappapi.requests;

// TODO: There is probably a way to DRY up simple request objects like this, and the GetHabit requests, unless there is some Spring thing I'm missing.
//       For now, I'm fine with just packaging them into a requests directory.

public class DeleteHabitByIdRequest {
    private Integer id;

    public DeleteHabitByIdRequest() {}

    public Integer getId() {
        return this.id;
    }
}
