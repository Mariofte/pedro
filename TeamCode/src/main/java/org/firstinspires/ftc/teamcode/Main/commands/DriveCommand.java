package org.firstinspires.ftc.teamcode.Main.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Main.subsystems.Drivetrain;

public class DriveCommand extends CommandBase {
    private Drivetrain drive;
    private GamepadEx gamepadEx;

    public DriveCommand(Drivetrain drive, GamepadEx gamepadEx)
    {
        this.drive = drive;
        this.gamepadEx = gamepadEx;

        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.start();
        drive.resetHeading();
    }

    @Override
    public void execute() {
        drive.setDrive(-gamepadEx.getLeftY(), gamepadEx.getLeftX(), gamepadEx.getRightX());
        drive.update();
    }
}
