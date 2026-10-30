package frc.robot.rappers.controllers;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.lib.state.DistanceChange;
import lombok.Getter;

public class XboxControllerRapper {
    @Getter
    private XboxController xboxController = null;

    public XboxControllerRapper(int port) {
        xboxController = new XboxController(port);
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

    public DistanceChange onDistanceChangeLeft(double distance, double deadband) {
        return new DistanceChange(distance, deadband, xboxController::getLeftX, xboxController::getLeftY);
    }
    public DistanceChange onDistanceChangeRight(double distance, double deadband) {
        return new DistanceChange(distance, deadband, xboxController::getRightX, xboxController::getRightY);
    }
    public DistanceChange onDistanceChangeLeftX(double distance, double deadband) {
        return new DistanceChange(distance, deadband, xboxController::getLeftX, () -> 0);
    }
    public DistanceChange onDistanceChangeRightX(double distance, double deadband) {
        return new DistanceChange(distance, deadband, xboxController::getRightX, () -> 0);
    }
    public DistanceChange onDistanceChangeLeftY(double distance, double deadband) {
        return new DistanceChange(distance, deadband, () -> 0, xboxController::getLeftY);
    }
    public DistanceChange onDistanceChangeRightY(double distance, double deadband) {
        return new DistanceChange(distance, deadband, () -> 0, xboxController::getRightY);
    }
}
