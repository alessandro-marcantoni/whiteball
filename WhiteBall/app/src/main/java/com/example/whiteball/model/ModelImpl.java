package com.example.whiteball.model;

import android.graphics.Point;

import com.example.whiteball.model.entities.build.EntityFactoryImpl;
import com.example.whiteball.model.entities.build.Spawner;
import com.example.whiteball.model.entities.build.SpawnerImpl;
import com.example.whiteball.model.entities.components.GravityComponent;
import com.example.whiteball.model.entities.components.MovementComponent;
import com.example.whiteball.model.entities.components.ToroidalComponent;
import com.example.whiteball.utility.Constants;
import com.example.whiteball.controller.Command;
import com.example.whiteball.model.entities.Ball;
import com.example.whiteball.model.entities.Entity;
import com.example.whiteball.model.entities.Rhombus;
import com.example.whiteball.model.entities.Square;
import com.example.whiteball.model.entities.Triangle;
import com.example.whiteball.model.entities.components.CollisionComponent;
import com.example.whiteball.model.entities.properties.Vector2DImpl;
import com.example.whiteball.model.entities.components.Component;
import com.example.whiteball.model.entities.components.ComponentType;
import com.example.whiteball.model.entities.components.InputComponent;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
    private final int Y_COORDINATE = Constants.SCREEN_HEIGHT - Constants.SCREEN_HEIGHT / 10;

    private Spawner spawner;
    private List<Entity> entities;
    private Ball player;
    private long time;

    public ModelImpl() {
        this.time = 0;
        this.entities = new ArrayList<>();
        this.player = new Ball(new Point(Constants.SCREEN_WIDTH / 2, Y_COORDINATE), Constants.PLAYER_RADIUS_INT);
        this.player.addComponent(new MovementComponent());
        this.player.addComponent(new ToroidalComponent());
        this.player.addComponent(new CollisionComponent());
        this.player.addComponent(new InputComponent());
        this.player.declarePlayer();

        this.entities.add(this.player);

        this.spawner = new SpawnerImpl();
/*
        Square square = (Square)EntityFactoryImpl.createSquare(new Point(200, 0));
        square.setVelocity(new Vector2DImpl(0, 1));
        this.entities.add(square);

        //Triangle triangle = (Triangle)EntityFactoryImpl.createTriangle(new Point(500, 0));
        //triangle.setVelocity(new Vector2DImpl(0, 1));
        //this.entities.add(triangle);

        Rhombus rhombus = (Rhombus)EntityFactoryImpl.createRhombus(new Point(800, 0));
        rhombus.setVelocity(new Vector2DImpl(0, 1));
        this.entities.add(rhombus);*/
    }

    @Override
    public boolean isGameOver() {
        for(Component component: this.player.getComponents()) {
            if(component.getType().equals(ComponentType.COLLISION)) {
                return ((CollisionComponent)component).anyCollision();
            }
        }
        return false;
    }

    @Override
    public void update(final long dt) {
        this.time += dt;

        if(!this.spawner.isSpawning() && this.time > 3000) {
            this.spawner.start();
        }
        this.spawner.update(this.entities, this.time);

        for (Entity entity: this.entities) {
            if (entity.isPlayer()) {
                for(Component component: entity.getComponents()) {
                    if(component.getType().equals(ComponentType.COLLISION)) {
                        ((CollisionComponent)component).updateEntities(this.entities);
                    }
                }
            }
            entity.update(dt);
        }
    }

    @Override
    public List<Entity> getEntities() {
        return ImmutableList.copyOf(this.entities);
    }

    @Override
    public Entity getPlayer() {
        return this.player;
    }

    @Override
    public void resolveInputs(List<Command> inputs) {
        // God let me use lambdas pt.2.
        for (Entity entity: this.entities) {
            for(Component component: entity.getComponents()) {
                if(component.getType().equals(ComponentType.INPUT)) {
                    ((InputComponent)component).collectInputs(inputs);
                }
            }
        }
    }

    @Override
    public long getElapsedTime() {
        return this.time;
    }
}
