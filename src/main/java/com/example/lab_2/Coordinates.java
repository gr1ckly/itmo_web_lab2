package com.example.lab_2;
import java.time.Duration;
import java.time.LocalDateTime;

public class Coordinates {
    private Double x;
    private Double y;
    private Double r;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private boolean hitting;

    public Coordinates(Double x, Double y, Double r, LocalDateTime startTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.startTime = startTime;
    }

    public Double getR() {
        return r;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public boolean isHitting() {
        return hitting;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public void setHitting(boolean hitting) {
        this.hitting = hitting;
    }
}
