package org.firstinspires.ftc.teamcode.Main.constants;

import com.pedropathing.localization.Encoder;
import com.pedropathing.localization.constants.TwoWheelConstants;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

public class RLConstants {
    static {
        TwoWheelConstants.forwardEncoder_HardwareMapName = "leftFront";
        TwoWheelConstants.forwardEncoderDirection = Encoder.REVERSE;
        TwoWheelConstants.forwardTicksToInches = .001989436789;
        TwoWheelConstants.forwardY = 1;

        TwoWheelConstants.strafeEncoder_HardwareMapName = "rightRear";
        TwoWheelConstants.strafeEncoderDirection = Encoder.FORWARD;
        TwoWheelConstants.strafeTicksToInches = .001989436789;
        TwoWheelConstants.strafeX = -2.5;

        TwoWheelConstants.IMU_HardwareMapName = "imu";
        TwoWheelConstants.IMU_Orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
        );
    }
}
