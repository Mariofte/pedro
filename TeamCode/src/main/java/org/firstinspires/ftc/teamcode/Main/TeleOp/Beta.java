package org.firstinspires.ftc.teamcode.Main.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Main.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Main.subsystems.Drivetrain;

@TeleOp(name = "Beta", group = "Test")
public class Beta extends CommandOpMode {
    private GamepadEx player;
    private Drivetrain drivetrain;
    
    @Override
    public void initialize() {
        player = new GamepadEx(gamepad1);
        drivetrain = new Drivetrain(hardwareMap, telemetry);

        drivetrain.setDefaultCommand(new DriveCommand(drivetrain, player, false, false));
    }
}
