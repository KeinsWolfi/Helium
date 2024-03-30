package me.helium9.util.render.Animation;

import lombok.Getter;
import lombok.Setter;
import me.helium9.util.Timer;

public abstract class Animation {

    public Timer timer = new Timer();
    @Setter
    protected int duration;
    @Getter
    @Setter
    protected double endPoint;
    @Getter
    protected Direction direction;

    public Animation(int duration, double endPoint) {
        this.duration = duration;
        this.endPoint = endPoint;
        this.direction = Direction.FORWARDS;
    }
    public Animation(int duration, double endPoint, Direction direction) {
        this.duration = duration;
        this.endPoint = endPoint;
        this.direction = direction;
    }

    public double getLinearOutput() {
        return 1 - ((timer.getTime() / (double) duration) * endPoint);
    }

    public void reset() {
        timer.reset();
    }

    public boolean isDone() {
        return timer.hasTimeElapsed(duration);
    }

    public void changeDirection() {
        setDirection(direction.opposite());
    }

    public Animation setDirection(Direction direction) {
        if (this.direction != direction) {
            this.direction = direction;
            timer.setTime(System.currentTimeMillis() - (duration - Math.min(duration, timer.getTime())));
        }
        return this;
    }

    protected boolean correctOutput() {
        return false;
    }

    public Double getOutput() {
        if (direction.forwards()) {
            if (isDone()) {
                return endPoint;
            }

            return getEquation(timer.getTime() / (double) duration) * endPoint;
        } else {
            if (isDone()) {
                return 0.0;
            }

            if (correctOutput()) {
                double revTime = Math.min(duration, Math.max(0, duration - timer.getTime()));
                return getEquation(revTime / (double) duration) * endPoint;
            }

            return (1 - getEquation(timer.getTime() / (double) duration)) * endPoint;
        }
    }


    //This is where the animation equation should go, for example, a logistic function. Output should range from 0 - 1.
    //This will take the timer's time as an input, x.
    protected abstract double getEquation(double x);

}
