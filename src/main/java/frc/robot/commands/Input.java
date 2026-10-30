package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Controllers;
import frc.robot.subsystems.Drivetrain;
import frc.robot.lib.state.DistanceChange;
import frc.robot.lib.state.DistanceState;
import frc.robot.Consts;

public class Input extends Command {
    Drivetrain drivetrain = null;
    Controllers controllers = null;
    DistanceChange distanceChangeLeft = null;
    DistanceChange distanceChangeRight = null;

    public Input(Drivetrain drivetrain, Controllers controllers) {
        addRequirements(drivetrain);

        this.drivetrain = drivetrain;
        this.controllers = controllers;
        distanceChangeLeft = this.controllers.primaryController.onDistanceChangeLeftY(Consts.ChangeThreshhold, Consts.DeadBand);
        distanceChangeRight = this.controllers.primaryController.onDistanceChangeRightY(Consts.ChangeThreshhold, Consts.DeadBand);
    }

    @Override
    public void initialize() {
        drivetrain.setSides(
                this.controllers.primaryController.LeftY(),
                this.controllers.primaryController.RightY());
    }

    @Override
    public void execute() {
        DistanceState leftState = distanceChangeLeft.getAsState();
        DistanceState rightState = distanceChangeRight.getAsState();

        if (leftState == DistanceState.NotChanged && rightState == DistanceState.NotChanged) return;

        double leftY = controllers.primaryController.LeftY();
        double rightY = controllers.primaryController.RightY();

        double leftAction = switch (leftState) {
            case Changed -> leftY;
            case DeadBand -> 0;
            case Max -> Math.copySign(1, leftY);
            case NotChanged -> Double.NaN;
        };
        double rightAction = switch (rightState) {
            case Changed -> rightY;
            case DeadBand -> 0;
            case Max -> Math.copySign(1, rightY);
            case NotChanged -> Double.NaN;
        };
        if (Double.isNaN(rightAction)) drivetrain.setLeft(leftAction);
        else if (Double.isNaN(leftAction)) drivetrain.setRight(rightAction);
        else drivetrain.setSides(leftAction, rightAction);
        
    }
}
