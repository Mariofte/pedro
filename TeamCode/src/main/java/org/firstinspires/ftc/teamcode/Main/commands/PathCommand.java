package org.firstinspires.ftc.teamcode.Main.commands;

import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Main.subsystems.Drivetrain;

public class PathCommand extends CommandBase {
    private Drivetrain drivetrain;
    private PathChain paths = new PathChain();
    private Path path;

    public PathCommand(Drivetrain drivetrain, Path path) {
        this.drivetrain = drivetrain;
        this.path = path;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {drivetrain.path(path);}

    @Override
    public void execute() {drivetrain.update();}

    @Override
    public void end(boolean interrupted) {drivetrain.stop();}

    @Override
    public boolean isFinished() {return drivetrain.finished();}
}
