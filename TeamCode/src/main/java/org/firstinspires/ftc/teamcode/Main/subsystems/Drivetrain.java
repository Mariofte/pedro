package org.firstinspires.ftc.teamcode.Main.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Vector;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Main.constants.RFConstants;
import org.firstinspires.ftc.teamcode.Main.constants.RLConstants;

import java.util.Locale;

public class Drivetrain extends SubsystemBase {
    private final Follower follower;
    private final Telemetry telemetry;
    private boolean isFieldCentric;
    private boolean lowSpeed = false;
    private double headingOffset = 0;

    public Drivetrain(HardwareMap hardwareMap, Telemetry telemetry, boolean isFieldCentric) {
        this.telemetry = telemetry;
        this.isFieldCentric = isFieldCentric;

        follower = new Follower(hardwareMap, RFConstants.class, RLConstants.class);
        follower.setStartingPose(new Pose(0, 0, 0));
    }

    public void start() {
        follower.startTeleopDrive();
    }

    public void update() {
        follower.update();
    }

    public Follower getFollower() {
        return follower;
    }

    // TODO El último parámetro indica si el modo es Robot-Centric (true) o Field-Centric (false)
    public void setDrive(double forward, double strafe, double heading) {
        if (lowSpeed) {
            forward *= 0.4;
            strafe *= 0.4;
            heading *= 0.4;
        }

        if (!isFieldCentric) {
            Vector input = fieldCentric(forward, strafe);
            follower.setTeleOpMovementVectors(input.getYComponent(), input.getXComponent(), heading, isFieldCentric);
        } else {
            follower.setTeleOpMovementVectors(forward, strafe, heading, true);
        }
    }

    public Vector fieldCentric(double y, double x) {
        double angle = getHeading();
        return rotated(angle, y, x);
    }

    public Vector rotated(double angle, double yval, double xval) {
        double newX = xval * Math.cos(-angle) - yval * Math.sin(-angle);
        double newY = xval * Math.sin(-angle) + yval * Math.cos(-angle);
        return new Vector(-newX, -newY);
    }

    public double getHeading() {
        return normalizeRadians(getPose().getHeading() - headingOffset);
    }

    public void resetHeading() {
        this.headingOffset = getPose().getHeading();
    }

    public double normalizeRadians(double angle) {
        while (angle > Math.PI) angle -= 2 * Math.PI;
        while (angle <= -Math.PI) angle += 2 * Math.PI;
        return angle;
    }

    public boolean isFieldCentric() {
        return isFieldCentric;
    }

    public void toggleFieldCentric() {
        isFieldCentric = !isFieldCentric;
    }

    public void setLowSpeed(boolean low) {
        this.lowSpeed = low;
    }

    public boolean isLowSpeed() {
        return lowSpeed;
    }

    public Pose getPose() {
        return follower.getPose();
    }

    public Vector getVelocity() {
        return follower.getVelocity();
    }

    @Override
    public void periodic() {
        String pos = String.format(Locale.US, "X: %.3f, Y: %.3f, H: %.3f", getPose().getX(), getPose().getY(), Math.toDegrees(getPose().getHeading()));
        String vel = String.format(Locale.US, "XVel: %.3f, YVel: %.3f, Magnitude: %.3f", getVelocity().getXComponent(), getVelocity().getYComponent(), getVelocity().getMagnitude());

        telemetry.addData("Position", pos);
        telemetry.addData("Velocity", vel);
    }
}
