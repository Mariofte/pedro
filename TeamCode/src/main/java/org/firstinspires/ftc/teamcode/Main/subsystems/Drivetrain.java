package org.firstinspires.ftc.teamcode.Main.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Vector;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Main.constants.RFConstants;
import org.firstinspires.ftc.teamcode.Main.constants.RLConstants;
import org.firstinspires.ftc.teamcode.Main.constants.Constants.DTConstants;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.lang.Math;

public class Drivetrain extends SubsystemBase {
    private Follower follower;
    private Telemetry telemetry;

    private boolean isFieldCentric;
    private boolean lowSpeed;
    private double headingOffset = 0;

    public Drivetrain(HardwareMap hardwareMap, Telemetry telemetry, boolean isFieldCentric, boolean lowSpeed) {
        this.telemetry = telemetry;
        this.isFieldCentric = isFieldCentric;
        this.lowSpeed = lowSpeed;

        follower = new Follower(hardwareMap, RFConstants.class, RLConstants.class);
        follower.setStartingPose(DTConstants.STARTING_POSE);
        setLowSpeed(lowSpeed);
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

    // TODO El último parámetro indica si el modo es Robot-Centric (flase) o Field-Centric (true)
    public void setDrive(double forward, double strafe, double heading) {
        if (lowSpeed) {
            forward *= DTConstants.LOW_SPEED_FACTOR;
            strafe *= DTConstants.LOW_SPEED_FACTOR;
            heading *= DTConstants.LOW_SPEED_FACTOR;
        }

        if (isFieldCentric) {
            Vector input = fieldCentric(forward, strafe);
            follower.setTeleOpMovementVectors(input.getYComponent(), input.getXComponent(), heading, false);
        } else if (!isFieldCentric) {
            follower.setTeleOpMovementVectors(forward, strafe, heading, true);
        }
    }

    public Vector fieldCentric(double y, double x) {
        double angle = getHeading();
        return rotated(angle, y, x);
    }

    // TODO: Dice que tote los joystick
    public Vector rotated(double angle, double yval, double xval) {
        double newX = xval * Math.cos(-angle) - yval * Math.sin(-angle);
        double newY = xval * Math.sin(-angle) + yval * Math.cos(-angle);
        return new Vector(-newX, -newY);
    }

    public double getHeading() {
        return normalizeRadians(getPose().getHeading() - headingOffset);
    }

    public double normalizeRadians(double angle) {
        while (angle > Math.PI) angle -= DTConstants.TWO_PI;
        while (angle <= -Math.PI) angle += DTConstants.TWO_PI;
        return angle;
    }

    public void resetHeading() {
        this.headingOffset = getPose().getHeading();
    }


    public boolean isFieldCentric() {
        return isFieldCentric;
    }

    public boolean isLowSpeed() {
        return lowSpeed;
    }

    // TODO: Dice que isFieldCentric falso
    public void toggleFieldCentric() {
        isFieldCentric = !isFieldCentric;
    }

    // TODO: La puedes llamar desde TeleOp para configurara
    public void setLowSpeed(boolean low) {
        this.lowSpeed = low;
    }

    public Pose getPose() {
        return follower.getPose();
    }

    public Vector getVelocity() {
        return follower.getVelocity();
    }

    public void simulator() throws JSONException, UnknownHostException {
        Pose2d pose2d = new Pose2d();
        JSONObject pose = new JSONObject();
        pose.put("x", pose2d.getX());
        pose.put("y", pose2d.getY());
        pose.put("stamp", System.currentTimeMillis() / 100.0);

        DatagramPacket packet = new DatagramPacket(
                pose.toString().getBytes(),
                pose.toString().length(),
                InetAddress.getByName("127.0.0.0"),9999
        );


    }

    @Override
    public void periodic() {
        String pos = String.format(Locale.US, "X: %.3f, Y: %.3f, H: %.3f", getPose().getX(), getPose().getY(), Math.toDegrees(getPose().getHeading()));
        String vel = String.format(Locale.US, "XVel: %.3f, YVel: %.3f, Magnitude: %.3f", getVelocity().getXComponent(), getVelocity().getYComponent(), getVelocity().getMagnitude());

        telemetry.addData("Position", pos);
        telemetry.addData("Velocity", vel);
    }
}