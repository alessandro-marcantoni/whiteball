package com.example.whiteball.controller;

import androidx.fragment.app.FragmentManager;

import com.example.whiteball.model.entities.Entity;
import com.example.whiteball.view.GameView;
import com.example.whiteball.model.Model;

import java.util.List;

public class ControllerImpl implements Controller, InputObserver {

    private GameView gameView;
    private Model model;
    private GameLoop gameLoop;
    private InputManager inputManager;
    private FragmentManager fragmentManager;

    public ControllerImpl(Model model, GameView gameView, FragmentManager fragmentManager) {
        this.model = model;
        this.gameView = gameView;
        this.inputManager = new InputManager();
        this.inputManager.addObserver(this);
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void startGameLoop() {
        this.gameLoop = new GameLoop(this.gameView, this.model, this.fragmentManager);
        this.gameLoop.startGameLoop();
    }

    @Override
    public void stopGameLoop() {
        this.inputManager.unregisterInput();
        this.gameLoop.stopGameLoop();
    }

    @Override
    public Double getAvgFPS() {
        return this.gameLoop.getAvgFPS();
    }

    @Override
    public List<Entity> getEntities() {
        return this.model.getEntities();
    }

    @Override
    public void pauseGameLoop() {
        this.inputManager.unregisterInput();
        this.gameLoop.pauseLoop();
    }

    @Override
    public void resumeGameLoop() {
        this.inputManager.registerInput();
        this.gameLoop.resumeLoop();
    }

    @Override
    public boolean isGameLoopPaused() { return this.gameLoop.isPaused(); }

    @Override
    public boolean isGameLoopRunning() {
        return this.gameLoop.isRunning();
    }

    @Override
    public long getElapsedTime() {
        return this.model.getElapsedTime();
    }

    @Override
    public void updateObserver(Command command) {
        this.gameLoop.addInput(command);
    }


}
