package org.firstinspires.ftc.teamcode.Main.Auto;

import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.Main.commands.PathCommand;
import org.firstinspires.ftc.teamcode.Main.subsystems.Drivetrain;

@Autonomous(name = "Auto", group = "Test")
public class Auto extends CommandOpMode {
    private Drivetrain drivetrain;

    @Override
    public void initialize() {
        drivetrain = new Drivetrain(hardwareMap, telemetry);

        schedule(new Sequence(drivetrain));
    }

    private static class Sequence extends SequentialCommandGroup {
        private Drivetrain drivetrain;

        public Sequence(Drivetrain drivetrain) {
            this.drivetrain = drivetrain;

            addRequirements(drivetrain);

            addCommands(
                    new PathCommand(
                            drivetrain,
                            new Path(new BezierLine(
                                    new Point(20, 20, Point.CARTESIAN),
                                    new Point(10, 20, Point.CARTESIAN)
                            ))
                    ),
                    new PathCommand(
                            drivetrain,
                            new Path(new BezierLine(
                                    new Point(10, 20, Point.CARTESIAN),
                                    new Point(10, 20, Point.CARTESIAN)
                            ))
                    )
            );
        }
    }
}
