package me.helium9.util.render.Animation.Animations;

import me.helium9.util.render.Animation.Animation;
import me.helium9.util.render.Animation.Direction;

public class EaseInOutSine extends Animation {

    public EaseInOutSine(int ms, double endPoint) {
        super(ms, endPoint);
    }

    public EaseInOutSine(int ms, double endPoint, Direction direction) {
        super(ms, endPoint, direction);
    }

    @Override
    protected boolean correctOutput() {
        return true;
    }

    @Override
    protected double getEquation(double x) {
        return Math.sin(x * (Math.PI / 2));
    }
}
