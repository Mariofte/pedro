package org.firstinspires.ftc.teamcode.Main.commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Main.subsystems.Drivetrain;

import java.util.function.DoubleSupplier;

public class DriveCommand extends CommandBase {
    public Drivetrain drive;
    public DoubleSupplier x, y, turn;

    public DriveCommand(DoubleSupplier y, DoubleSupplier x, DoubleSupplier turn, Drivetrain drive)
    {
        this.drive = drive;
        this.y = y;
        this.x = x;
        this.turn = turn;

        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.start();
        drive.resetHeading();
    }

    @Override
    public void execute() {
        drive.setDrive(y.getAsDouble(), x.getAsDouble(), turn.getAsDouble());
        drive.update();
    }
}
