
package frc.robot;

//import java.io.File;

import edu.wpi.first.math.MathUtil;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.util.Poses;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
//import edu.wpi.first.wpilibj.Filesystem;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;

public class RobotContainer {
        private final SwerveSubsystem drivebase = SwerveSubsystem.getInstance();
        Poses poses = new Poses();
        CommandXboxController driverController = new CommandXboxController(0);
        CommandXboxController operatorController = new CommandXboxController(1);
        XboxController xbox = new XboxController(0);
        Trigger twoBumper = new Trigger(
                        () -> (driverController.getRawAxis(2) > 0.85 && driverController.getRawAxis(3) > 0.85));
        Command driveFieldOrientedAngularVelocity;

        public RobotContainer() {

                //trocar velocidade máxima/change max speed/ drivercontrollerspeed
                driveFieldOrientedAngularVelocity = drivebase.driveCommand(
                                () -> MathUtil.applyDeadband(driverController.getLeftY(),
                                                OperatorConstants.kLeftYDeadBand),
                                () -> MathUtil.applyDeadband(driverController.getLeftX(),
                                                OperatorConstants.kLeftXDeadBand),
                                () -> (driverController.getRawAxis(2) - driverController.getRawAxis(3)));
        }

        // Configura os botões do Xbox.
        void configureBindings() {
                // Controle do piloto
                driverController.povRight().onTrue(new InstantCommand(drivebase::zeroGyro));
                // driverController.x().onTrue(new AlignToAmp());
              
                twoBumper
                                .onTrue(new InstantCommand(() -> xbox.setRumble(RumbleType.kBothRumble, 1)))
                                .onFalse((new InstantCommand(() -> xbox.setRumble(RumbleType.kBothRumble, 0))));

                drivebase.setDefaultCommand(driveFieldOrientedAngularVelocity);

        }


}