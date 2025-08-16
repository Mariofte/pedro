package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Main.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

import java.util.ArrayList;

@Autonomous
public class FollowPedroSample extends CommandOpMode {
    Drivetrain drivetrain = new Drivetrain(hardwareMap, telemetry);
    private final ArrayList<PathChain> paths = new ArrayList<>();
    PathChain pathChain = new PathChain();
    Path path = new Path(
            new BezierCurve(
                    new Point(20, 30, Point.CARTESIAN),
                    new Point(50, 80, Point.CARTESIAN)
            )
    );

    public void generatePath() {
        paths.add(
                // An example path
                drivetrain.getFollower().pathBuilder()
                        .addPath(
                                path
                        )
               .build()
        );
    }

    @Override
    public void initialize() {
        super.reset();

        generatePath();

        schedule(
                // Updates follower to follow path
                new RunCommand(() -> drivetrain.update()),

                new FollowPathCommand(drivetrain.getFollower(), paths.get(0)),
                new FollowPathCommand(drivetrain.getFollower(), pathChain),
                new FollowPathCommand(drivetrain.getFollower(), path)
        );
    }

    @Override
    public void run() {
        super.run();

    }
}