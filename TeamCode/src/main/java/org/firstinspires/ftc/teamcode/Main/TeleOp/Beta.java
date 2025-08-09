package org.firstinspires.ftc.teamcode.Main.TeleOp;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Main.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Main.subsystems.Drivetrain;

public class Beta extends CommandOpMode {
    @Override
    public void initialize()
    {
        GamepadEx player = new GamepadEx(gamepad1);

        Drivetrain drive = new Drivetrain(hardwareMap, telemetry, false);
        drive.resetHeading();
        drive.setLowSpeed(false);

        drive.setDefaultCommand(new DriveCommand(
                player::getLeftY,
                player::getLeftX,
                player::getRightX,
                drive
        ));

        schedule(new RunCommand(telemetry::update));
    }
}
