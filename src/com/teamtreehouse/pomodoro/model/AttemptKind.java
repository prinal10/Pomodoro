package com.teamtreehouse.pomodoro.model;

/**
 * Created by psp58 on 1/2/2017.
 */
public enum AttemptKind {
    FOCUS(4, "Focus Time"),
    BREAK(4, "Break Time");

    private int mTotalSeconds;
    private String mDisplayName;

    AttemptKind(int totalSeconds, String displayName) {
        mTotalSeconds = totalSeconds;
        mDisplayName = displayName;
    }

    public int getTotalSeconds() {
        return mTotalSeconds;
    }

    public String getDisplayName() {
        return mDisplayName;
    }
}
