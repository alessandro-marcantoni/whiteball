package com.example.whiteball.model.entities;

import android.graphics.Canvas;
import android.graphics.Point;

import com.example.whiteball.model.entities.properties.Area;
import com.example.whiteball.model.entities.properties.Velocity;

public interface Entity {
    void draw(Canvas canvas);

    void setPosition(Point point);

    Point getPosition();

    EntityType getType();

    Velocity getVelocity();

    void setVelocity(Velocity velocity);

    int getRadius();

    Area getArea();

}
