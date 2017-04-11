package com.teamtreehouse.pomodoro.controllers;

import com.teamtreehouse.pomodoro.model.Attempt;
import com.teamtreehouse.pomodoro.model.AttemptKind;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

/**
 * Created by psp58 on 1/2/2017.
 */
public class Home {
    @FXML
    private VBox container;
    @FXML
    private Label title;
    @FXML
    private TextArea message;
    @FXML
    private Button play;


    private Attempt mCurrentAttempt;
    private StringProperty mTimerText;
    private Timeline mTimeLine;
    private final AudioClip mApplause;



    public Home() {
        mTimerText = new SimpleStringProperty();

        //Due to this the clock initially displays 00:00
        setTimerText(0);
        mApplause = new AudioClip(getClass().getResource("/sounds/applause.mp3").toExternalForm());
    }


    // returns the mTimerText value
    public String getTimerText() {
        return mTimerText.get();
    }


    //set the mTimerText value
    public void setTimerText(String timerText) {
        this.mTimerText.set(timerText);
    }


    // returns the StringProperty object
    public StringProperty timerTextProperty() {
        return mTimerText;
    }

    public void setTimerText(int remainingSeconds){

        int minutes = remainingSeconds/60;
        int seconds = remainingSeconds%60;

        setTimerText(String.format("%02d:%02d",minutes,seconds));
        //System.out.println("Hello-1");

    }



    private void prepareAttempt(AttemptKind kind) {
        reset();
        mCurrentAttempt = new Attempt(kind, "");
        addAttemptStyle(kind);
        title.setText(kind.getDisplayName());
        //System.out.println("Before the second one----");
        setTimerText(mCurrentAttempt.getRemainingSeconds());
        mTimeLine = new Timeline();
        mTimeLine.setCycleCount(kind.getTotalSeconds());
        mTimeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
            //System.out.println("Hello-2");
            mCurrentAttempt.tick();
            setTimerText(mCurrentAttempt.getRemainingSeconds());

        }));

        mTimeLine.setOnFinished(e -> {
            saveCurrentAttempt();
            mApplause.play();
            prepareAttempt(mCurrentAttempt.getKind() == AttemptKind.FOCUS ?
                            AttemptKind.BREAK : AttemptKind.FOCUS);
            play.setText("Start");
        });
        //System.out.println("Hello-3");


    }

    private void saveCurrentAttempt() {
        mCurrentAttempt.setMessage(message.getText());
        mCurrentAttempt.save();
    }

    private void reset() {
        clearAttemptStyles();
        if(mTimeLine !=null && mTimeLine.getStatus() == Animation.Status.RUNNING){
            mTimeLine.stop();
        }
    }

    public void playTimer(){

        container.getStyleClass().add("playing");

        //System.out.println("Hello-4");
        mTimeLine.play();

    }

    public void pauseTimer(){
        container.getStyleClass().remove("playing");
        mTimeLine.pause();
    }

    private void addAttemptStyle(AttemptKind kind) {
        container.getStyleClass().add(kind.toString().toLowerCase());  //it returns a list of style class


    }

    private void clearAttemptStyles() {
        container.getStyleClass().remove("playing");
        for (AttemptKind kind : AttemptKind.values()) {

            container.getStyleClass().removeAll(kind.toString().toLowerCase());

        }
    }


    public void handleRestart(ActionEvent actionEvent) {
        prepareAttempt(AttemptKind.FOCUS);
        playTimer();
    }

    public void handleResume(ActionEvent actionEvent) {

        play.setText("Resume");

        if (mCurrentAttempt == null){
            handleRestart(actionEvent);
        }
        else{

            playTimer();
        }
    }

    public void handlePause(ActionEvent actionEvent) {
        pauseTimer();
    }
}
