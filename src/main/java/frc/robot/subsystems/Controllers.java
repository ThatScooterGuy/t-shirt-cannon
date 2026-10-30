package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Consts;
import frc.robot.rappers.controllers.XboxControllerRapper;

public class Controllers extends SubsystemBase {
    public XboxControllerRapper primaryController = null;
    public XboxControllerRapper secondaryController = null;
    public Controllers() {
        primaryController = new XboxControllerRapper(Consts.PrimaryControllerPort);
        secondaryController = new XboxControllerRapper(Consts.SecondaryControllerPort);
    }
}
