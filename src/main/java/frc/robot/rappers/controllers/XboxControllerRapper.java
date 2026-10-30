package frc.robot.rappers.controllers;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.XboxController;
import lombok.Getter;

public class XboxControllerRapper {
    @Getter
    private XboxController xboxController = null;

    public XboxControllerRapper(int port) {
        xboxController = new XboxController(port);
    }

    public static class DistanceChange implements BooleanSupplier {
        private DoubleSupplier xSupplier = null;
        private DoubleSupplier ySupplier = null;
        private double threshhold = 0;
        private double refx = 0;
        private double refy = 0;

        private static double distance(double x, double y, double dx, double dy) {
            return Math.hypot(x - dx, y - dy);
        }

        public DistanceChange(double threshhold, DoubleSupplier xSupplier, DoubleSupplier ySupplier) {
            this.xSupplier = xSupplier;
            this.ySupplier = ySupplier;
            this.threshhold = threshhold;
        }

        @Override
        public boolean getAsBoolean() {
            double newRefx = xSupplier.getAsDouble();
            double newRefy = ySupplier.getAsDouble();

            double changeDistance = distance(refx, refy, newRefx, newRefy);
            double distanceFromCenter = distance(0, 0, newRefx, newRefy);

            if (threshhold < changeDistance || distanceFromCenter > 1 - threshhold) {
                refx = newRefx;
                refy = newRefy;
            }
            else if (distanceFromCenter < threshhold + 0.01) {
                refx = 0;
                refy = 0;
            } else return false;
            return true;
        }
    }

    public double LeftX() {
        return this.xboxController.getLeftX();
    }
    public double RightX() {
        return this.xboxController.getRightX();
    }
    public double LeftY() {
        return this.xboxController.getLeftY();
    }
    public double RightY() {
        return this.xboxController.getRightY();
    }

    public DistanceChange onDistanceChangeLeft(double distance) {
        return new DistanceChange(distance, xboxController::getLeftX, xboxController::getLeftY);
    }
    public DistanceChange onDistanceChangeRight(double distance) {
        return new DistanceChange(distance, xboxController::getRightX, xboxController::getRightY);
    }
    public DistanceChange onDistanceChangeLeftX(double distance) {
        return new DistanceChange(distance, xboxController::getLeftX, () -> 0);
    }
    public DistanceChange onDistanceChangeRightX(double distance) {
        return new DistanceChange(distance, xboxController::getRightX, () -> 0);
    }
    public DistanceChange onDistanceChangeLeftY(double distance) {
        return new DistanceChange(distance, () -> 0, xboxController::getLeftY);
    }
    public DistanceChange onDistanceChangeRightY(double distance) {
        return new DistanceChange(distance, () -> 0, xboxController::getRightY);
    }
}
