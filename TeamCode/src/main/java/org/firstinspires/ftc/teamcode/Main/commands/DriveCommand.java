package org.firstinspires.ftc.teamcode.Main.commands;

import com.pedropathing.pathgen.Vector;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Main.constants.Constants.DTConstants;
import org.firstinspires.ftc.teamcode.Main.subsystems.Drivetrain;

public class DriveCommand extends CommandBase {
    private Drivetrain drivetrain;
    private GamepadEx gamepadEx;
    private boolean isFieldCentric;
    private boolean lowSpeed;

    public DriveCommand(Drivetrain drivetrain, GamepadEx gamepadEx, boolean isFieldCentric, boolean lowSpeed) {
        this.drivetrain = drivetrain;
        this.gamepadEx = gamepadEx;
        this.isFieldCentric = isFieldCentric;
        this.lowSpeed = lowSpeed;

        addRequirements(drivetrain);
    }

    private Vector FieldCentric(double y, double x) {
        double angle = drivetrain.normalizeRadians(drivetrain.getPose().getHeading());
        return drivetrain.rotated(angle, x, y);
    }

    @Override
    public void initialize() {drivetrain.start();}

    @Override
    public void execute() {
        double forward = -gamepadEx.getLeftY();
        double strafe = gamepadEx.getLeftX();
        double turn = gamepadEx.getRightX();

        if (lowSpeed == true) {
            forward *= DTConstants.LOW_SPEED_FACTOR;
            strafe *= DTConstants.LOW_SPEED_FACTOR;
            turn *= DTConstants.LOW_SPEED_FACTOR;
        }

        if (isFieldCentric == true) {
            Vector input = FieldCentric(forward, strafe);
            drivetrain.setDrive(input.getYComponent(), input.getXComponent(), turn, false);
        } else if (isFieldCentric == false) {
            drivetrain.setDrive(forward, strafe, turn, true);
        }
    }

    @Override
    public void end(boolean interrupted) {drivetrain.stop();}

    @Override
    public boolean isFinished() {return super.isFinished();}
}
