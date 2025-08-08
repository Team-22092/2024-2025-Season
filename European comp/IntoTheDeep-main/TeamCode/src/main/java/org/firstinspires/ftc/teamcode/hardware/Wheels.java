package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Wheels {
    public static double WHEELS_SLOW_MODE_PERCENT = 0.33;

    HardwareMap hardwareMap;
    private DcMotor FLMotor, FRMotor, BRMotor, BLMotor;

    double[] speeds = new double[4];
    private double drive, strafe, turn, max;


    public Wheels(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        FLMotor = this.hardwareMap.get(DcMotor.class, "LF");
        FRMotor = this.hardwareMap.get(DcMotor.class, "RF");
        BRMotor = this.hardwareMap.get(DcMotor.class, "RB");
        BLMotor = this.hardwareMap.get(DcMotor.class, "LB");

        FLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        FLMotor.setDirection(DcMotor.Direction.REVERSE);
        FRMotor.setDirection(DcMotor.Direction.FORWARD);
        BRMotor.setDirection(DcMotor.Direction.FORWARD);
        BLMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void manual_drive(Gamepad driving_gamepad) {
        drive = driving_gamepad.left_stick_y;
        strafe = driving_gamepad.left_stick_x;
        turn = driving_gamepad.right_stick_x;

        speeds[0] = -drive + turn + strafe;
        speeds[1] = -drive - turn - strafe;
        speeds[2] = -drive + turn - strafe;
        speeds[3] = -drive - turn + strafe;

        max = Math.abs(speeds[0]);
        for (int i = 1; i < speeds.length; ++i) {
            if (max < Math.abs(speeds[i])) max = Math.abs(speeds[i]);
        }

        if (max > 1) {
            for (int i = 0; i < speeds.length; ++i) speeds[i] /= max;
        }

        if (driving_gamepad.right_bumper) {
            FLMotor.setPower(speeds[0] * WHEELS_SLOW_MODE_PERCENT);
            FRMotor.setPower(speeds[1] * WHEELS_SLOW_MODE_PERCENT);
            BLMotor.setPower(speeds[2] * WHEELS_SLOW_MODE_PERCENT);
            BRMotor.setPower(speeds[3] * WHEELS_SLOW_MODE_PERCENT);
        } else {
            FLMotor.setPower(speeds[0]);
            FRMotor.setPower(speeds[1]);
            BLMotor.setPower(speeds[2]);
            BRMotor.setPower(speeds[3]);
        }
    }

}
