package frc.robot.lib.state;

import java.util.function.DoubleSupplier;

public class DistanceChange implements StateSupplier {
    private DoubleSupplier xSupplier = null;
    private DoubleSupplier ySupplier = null;
    private double threshhold = 0;
    private double deadband = 0;
    private double refx = 0;
    private double refy = 0;
    private State state = State.Neither;


    private static enum State {
        DeadBand,
        Max,
        Neither
    }


    private static double distance(double x, double y, double dx, double dy) {
        return Math.hypot(x - dx, y - dy);
    }

    public DistanceChange(double threshhold, double deadband, DoubleSupplier xSupplier, DoubleSupplier ySupplier) {
        this.xSupplier = xSupplier;
        this.ySupplier = ySupplier;
        this.threshhold = threshhold;
        this.deadband = deadband;
    }

    @Override
    public DistanceState getAsState() {
        double newRefx = xSupplier.getAsDouble();
        double newRefy = ySupplier.getAsDouble();

        double changeDistance = distance(refx, refy, newRefx, newRefy);
        double distanceFromCenter = distance(0, 0, newRefx, newRefy);

        if (threshhold < changeDistance) {
            refx = newRefx;
            refy = newRefy;
            state = State.Neither;
            return DistanceState.Changed;
        }
        if (distanceFromCenter > 1 - threshhold && state != State.Max) {
            refx = newRefx;
            refy = newRefy;
            state = State.Max;
            return DistanceState.Max;
        }
        if (distanceFromCenter < deadband && state != State.DeadBand) {
            refx = 0;
            refy = 0;
            state = State.DeadBand;
            return DistanceState.DeadBand;
        }
        return DistanceState.NotChanged;
    }
}