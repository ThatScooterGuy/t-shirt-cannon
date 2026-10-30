package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.rappers.controllers.XboxControllerRapper.DistanceChange;
import frc.robot.subsystems.Controllers;
import frc.robot.subsystems.Drivetrain;

public class Input extends Command {
    Drivetrain drivetrain = null;
    Controllers controllers = null;
    DistanceChange distanceChangeLeft = null;
    DistanceChange distanceChangeRight = null;

    private void updateWithPrimaryAxis() {
        drivetrain.setSides(
            this.controllers.primaryController.LeftY(), 
            this.controllers.primaryController.RightY());
    }

    public Input(Drivetrain drivetrain, Controllers controllers) {
        addRequirements(drivetrain);
        
        this.drivetrain = drivetrain;
        this.controllers = controllers;
        distanceChangeLeft = this.controllers.primaryController.onDistanceChangeLeftY(0.05);
        distanceChangeRight = this.controllers.primaryController.onDistanceChangeRightY(0.05);
    }

    @Override public void initialize() {
        updateWithPrimaryAxis();
    }

    @Override public void execute() {
        if (distanceChangeLeft.getAsBoolean() || distanceChangeRight.getAsBoolean()) {
            updateWithPrimaryAxis();
        }
    }
}
