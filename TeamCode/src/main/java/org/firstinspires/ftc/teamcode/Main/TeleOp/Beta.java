package org.firstinspires.ftc.teamcode.Main.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Main.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Main.subsystems.Drivetrain;

@TeleOp(name = "Beta", group = "Test")
public class Beta extends CommandOpMode {
    @Override
    public void initialize()
    {
        GamepadEx player = new GamepadEx(gamepad1);

        Drivetrain drive = new Drivetrain(hardwareMap, telemetry, true, true);

        drive.setDefaultCommand(new DriveCommand(
                player::getLeftY,
                player::getLeftX,
                player::getRightX,
                drive
        ));

        schedule(new RunCommand(telemetry::update));
    }
}
