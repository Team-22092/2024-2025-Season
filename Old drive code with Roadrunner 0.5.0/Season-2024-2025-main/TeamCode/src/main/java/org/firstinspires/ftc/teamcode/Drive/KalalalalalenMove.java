package org.firstinspires.ftc.teamcode.Drive;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//package org.openftc.i2cdrivers;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Auto.OpenCVCubeBLUE;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "KalalalalalenMove")






public class KalalalalalenMove extends OpMode {


    private double MAXOUTPOWER = 0.5;



    private double drive, strafe, turn, armPower, slidesPower = 0.0;

    double max;
    double[] speeds = new double[4];



    public OpenCVCubeBLUE openCVCubeBLUE;




    double slideup, slideout;


    private DcMotor FLMotor;
    private DcMotor FRMotor;
    private DcMotor BRMotor;
    private DcMotor BLMotor;
    private DcMotor SlideUP;
    private DcMotor SLideOut;
    private CRServo bucketServo;
    private Servo FlipServo;
    private Servo SigmaServo;


    @Override
    public void init() {
        telemetry.addLine(">> Welcome :)");
        telemetry.update();


        FLMotor  = hardwareMap.get(DcMotor.class, "FL");
        FRMotor  = hardwareMap.get(DcMotor.class, "FR");
        BRMotor  = hardwareMap.get(DcMotor.class, "BR");
        BLMotor  = hardwareMap.get(DcMotor.class, "BL");

        SigmaServo  = hardwareMap.get(Servo.class, "sigmaServo");





        SlideUP  = hardwareMap.get(DcMotor.class, "Up");
        SLideOut  = hardwareMap.get(DcMotor.class, "Out");


        FLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        FRMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        BRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        BLMotor.setDirection(DcMotorSimple.Direction.FORWARD);


        SlideUP.setDirection(DcMotorSimple.Direction.FORWARD);
        SLideOut.setDirection(DcMotorSimple.Direction.FORWARD);

        bucketServo = hardwareMap.get(CRServo.class, "Bucket");


        FlipServo = hardwareMap.get(Servo.class, "FS");

    }

    @Override
    public void loop() {

        //back and force moytoros
        drive = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        // Set motor power
        speeds[0] = -drive + turn + strafe;
        speeds[1] = -drive - turn - strafe;
        speeds[2] = -drive + turn - strafe;
        speeds[3] = -drive - turn + strafe;

        max = Math.abs(speeds[0]);
        for(int i = 1; i < speeds.length; ++i) {
            if ( max < Math.abs(speeds[i]) ) max = Math.abs(speeds[i]);
        }

        if (max > 1) {
            for (int i = 0; i < speeds.length; ++i) speeds[i] /= max;
        }




        slideup = gamepad2.dpad_right ? MAXOUTPOWER : gamepad2.dpad_left ? -MAXOUTPOWER : 0;

        slideout = gamepad2.dpad_up ? MAXOUTPOWER : gamepad2.dpad_down ? -MAXOUTPOWER : 0;


        if(gamepad2.a)
        {
            bucketServo.setPower(1);
        }
        else if(gamepad2.b)
        {
            bucketServo.setPower(-1);
        }
        else
        {
            bucketServo.setPower(0);
        }


        if(gamepad2.y)
        {
            FlipServo.setPosition(0.7);
        }

        if(gamepad2.x)
        {
            FlipServo.setPosition(-1);
        }

        if(gamepad2.right_bumper)
        {
            SigmaServo.setPosition(-0.2);
        }

        if(gamepad2.left_bumper)
        {
            SigmaServo.setPosition(0.2);
        }








        FLMotor.setPower(speeds[0]);
        FRMotor.setPower(speeds[1]);
        BLMotor.setPower(speeds[2]);
        BRMotor.setPower(speeds[3]);


        SlideUP.setPower(slideup);
        SLideOut.setPower(slideout);

        telemetry.addData("Slide out", slideout);
        telemetry.addData("Slide up", slideup);


    }

    @Override
    public void stop() {

        // Stop all motors if no input and if gamestop

        FLMotor.setPower(0);
        FRMotor.setPower(0);
        BRMotor.setPower(0);
        BLMotor.setPower(0);

        SlideUP.setPower(0);
        SLideOut.setPower(0);
        bucketServo.setPower(0);
        FlipServo.setPosition(0);

    }
}