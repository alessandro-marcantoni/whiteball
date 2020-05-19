package com.example.whiteball.controller;

import android.widget.Toast;

import com.example.whiteball.Constants;
import com.example.whiteball.model.Model;
import com.example.whiteball.view.GameView;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class GameLoop extends Thread {
    private static final int FPS = 60;
    private final long frameRate;

    private GameView gameView;
    private Model model;
    private double avgFPS = 0;
    private boolean isRunning;
    private boolean paused;
    private List<Command> commands;

    public GameLoop(GameView gameView, Model model) {
        super();
        this.isRunning = false;
        this.paused = false;

        this.gameView = gameView;
        this.model = model;
        this.frameRate = (long) (1 / (this.FPS * 0.001));
        this.commands = new ArrayList<>();
    }

    @Override
    public void run() {
        super.run();
        int frameCount = 0;
        long lastTime = System.currentTimeMillis();
        long startTime = lastTime;
        while(this.isRunning) {
            final long currentTime = System.currentTimeMillis();

            if(this.model.isGameOver()) {
                this.paused = true;
            }

            //Heart of the game loop: taking input, updating and rendering
            this.processInput();
            final long elapsedTime = currentTime - lastTime;

            if(!this.paused) {
                this.update(elapsedTime);
                //la render viene fatta automaticamente dalla view
            }

            frameCount++;

            //Pause game loop to reach the target FPS
            this.waitForNextFrame(currentTime);

            //Calculate FPS
            long frameTime = System.currentTimeMillis() - startTime;
            if(frameTime >= 1000) {
                avgFPS =  frameCount / (1E-3 * frameTime);
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }

            lastTime = currentTime;
        }
    }

    public double getAvgFPS() {
        return this.avgFPS;
    }

    public void startGameLoop() {
        this.isRunning = true;
        this.start();
    }

    public void stopGameLoop() {
        this.isRunning = false;
    }

    public void addInput(Command command) {
        this.commands.add(command);
    }

    public boolean isPaused() { return this.paused; };

    public void pauseLoop() { this.paused = true; }

    public void resumeLoop() { this.paused = false; }

    private void processInput() {
        this.model.resolveInputs(ImmutableList.copyOf(this.commands));
        this.commands = new ArrayList<>();
    }

    private void update(final long elapsedTime) {
        this.model.update(elapsedTime);
    }

    private void waitForNextFrame(final long currentTime) {
        final long dt = System.currentTimeMillis() - currentTime;
        if (dt < this.frameRate) {
            try {
                Thread.sleep(this.frameRate - dt);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
