package com.teamtreehouse.pomodoro.model;

/**
 * Created by psp58 on 1/2/2017.
 */
public class Attempt {

    private String mMessage;
    private int mRemainingSeconds;
    private AttemptKind mKind;

    public Attempt(AttemptKind kind, String message) {
        mMessage = message;
        mKind = kind;
        mRemainingSeconds = kind.getTotalSeconds();
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public int getRemainingSeconds() {
        return mRemainingSeconds;
    }

    public AttemptKind getKind() {
        return mKind;
    }

    public void tick() {
        mRemainingSeconds--;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "\n mMessage='" + mMessage + '\'' +
                ", \n mRemainingSeconds=" + mRemainingSeconds +
                ", \n mKind=" + mKind +
                '}';
    }

    public void save() {
        System.out.println("Saving.......: "+this);
    }
}
