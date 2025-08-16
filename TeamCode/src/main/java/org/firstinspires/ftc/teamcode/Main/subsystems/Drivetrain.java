    package org.firstinspires.ftc.teamcode.Main.subsystems;

    import com.pedropathing.follower.Follower;
    import com.pedropathing.localization.Pose;
    import com.pedropathing.pathgen.Path;
    import com.pedropathing.pathgen.Vector;
    import com.qualcomm.robotcore.hardware.HardwareMap;
    import com.seattlesolvers.solverslib.command.SubsystemBase;

    import org.firstinspires.ftc.robotcore.external.Telemetry;
    import org.firstinspires.ftc.teamcode.Main.constants.Constants.DTConstants;
    import org.firstinspires.ftc.teamcode.Main.constants.RFConstants;
    import org.firstinspires.ftc.teamcode.Main.constants.RLConstants;

    import java.lang.Math;
    import java.util.Locale;

    public class Drivetrain extends SubsystemBase {
        private Follower follower;
        private HardwareMap hardwareMap;
        private Telemetry telemetry;

        public Drivetrain(HardwareMap hardwareMap, Telemetry telemetry) {
            this.hardwareMap = hardwareMap;
            this.telemetry = telemetry;

            follower = new Follower(hardwareMap, RFConstants.class, RLConstants.class);
            follower.setStartingPose(DTConstants.STARTING_POSE);
        }

        public void start() {follower.startTeleopDrive();}
        public void path(Path path) {follower.followPath(path);}
        public boolean finished () {return !follower.isBusy();}
        public void update() {follower.update();}
        public void stop() {follower.breakFollowing();}
        public Follower getFollower() {return follower;}

        public void setDrive(double y, double x, double turn, boolean isFieldCentric) {
            follower.setTeleOpMovementVectors(y, x, turn, isFieldCentric);
        }

        public Vector rotated(double angle, double xval, double yval) {
            double newX = xval * Math.cos(angle) - yval * Math.sin(angle);
            double newY = xval * Math.sin(angle) + yval * Math.cos(angle);
            return new Vector(newX, newY);
        }

        public double normalizeRadians(double angle) {
            while (angle > Math.PI) angle -= DTConstants.TWO_PI;
            while (angle <= -Math.PI) angle += DTConstants.TWO_PI;
            return angle;
        }


        public Pose getPose() {return follower.getPose();}

        public Vector getVelocity() {return follower.getVelocity();}

        @Override
        public void periodic() {
            String pos = String.format(Locale.US, "X: %.3f, Y: %.3f, H: %.3f", getPose().getX(), getPose().getY(), Math.toDegrees(getPose().getHeading()));
            String vel = String.format(Locale.US, "XVel: %.3f, YVel: %.3f, Magnitude: %.3f", getVelocity().getXComponent(), getVelocity().getYComponent(), getVelocity().getMagnitude());

            telemetry.addData("Position", pos);
            telemetry.addData("Velocity", vel);
        }
    }