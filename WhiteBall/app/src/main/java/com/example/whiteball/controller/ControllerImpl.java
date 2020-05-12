package com.example.whiteball.controller;

import android.hardware.SensorManager;

import com.example.whiteball.model.entities.Entity;
import com.example.whiteball.view.GameView;
import com.example.whiteball.view.GameViewImpl;
import com.example.whiteball.model.Model;

import java.util.List;

public class ControllerImpl implements Controller {

    private GameView gameView;
    private Model model;
    private GameLoop gameLoop;
    private Orientation orientation;

    public ControllerImpl(Model model, GameView gameView) {
        this.model = model;
        this.gameView = gameView;
        this.orientation = new Orientation(this);
    }

    @Override
    public void startGameLoop() {
        this.gameLoop = new GameLoop(this.gameView, this);
        this.gameLoop.startGameLoop();
    }

    //@Override
    public void stopGameLoop() {
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
    public void update() {
        this.orientation.execute(this.model);
    }



}
