package com.whiteship.inflearnthejavatest;

public class Study {

    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;

    public Study(int limit) {
        this.limit = limit;
    }

    public StudyStatus getStatus() {
        return status;
    }

    public int getLimit() {
        return limit;
    }
}
