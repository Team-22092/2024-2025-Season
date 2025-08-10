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
import org.firstinspires.ftc.teamcode.PIDController;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Drive")






public class KalalalalalenMove extends OpMode {



    private double maxHight = -4500;
    private double tallPole = -2049;
    private double smallPole = -929;

    private int currentLevel = -1;
    private int P2currentLevel = -1;


    private boolean isSwichUp, isP2SwichUp = true;


    private double MAXOUTPOWER = 0.5;



    private double drive, strafe, turn, armPower, slidesPower = 0.0;

    double max;
    double[] speeds = new double[4];





    double kp = 0.0025, ki = 0.0, kd = 0.000;
    public PIDController pidArm = new PIDController(kp, ki ,kd);


    double slideup, slideout;


    private DcMotor FLMotor;
    private DcMotor FRMotor;
    private DcMotor BRMotor;
    private DcMotor BLMotor;
    private DcMotor SlideUP;
    private DcMotor SLideOut;


    //old
//    private CRServo bucketServo;
//    private Servo FlipServo;
//    private Servo DumpServo;



    boolean enable = true;
    boolean position = true;//true is open, false is closed



    boolean flipon = true;
    boolean flipposition = false;//true is open, false is closed
    private int HoriTransferPos = 500;

    //double

    double error;

    @Override
    public void init() {
        telemetry.addLine(">> Welcome :)");
        telemetry.update();


        FLMotor  = hardwareMap.get(DcMotor.class, "leftFront");
        FRMotor  = hardwareMap.get(DcMotor.class, "rightFront");
        BRMotor  = hardwareMap.get(DcMotor.class, "rightBack");
        BLMotor  = hardwareMap.get(DcMotor.class, "leftBack");


        //old
//        DumpServo  = hardwareMap.get(Servo.class, "trayServo");





        SlideUP  = hardwareMap.get(DcMotor.class, "Up");
        SLideOut  = hardwareMap.get(DcMotor.class, "Out");


        FLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        FRMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        BRMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        BLMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        SlideUP.setDirection(DcMotorSimple.Direction.FORWARD);
        SLideOut.setDirection(DcMotorSimple.Direction.FORWARD);


        SLideOut.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SlideUP.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        SLideOut.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        SlideUP.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



        //old

//        bucketServo = hardwareMap.get(CRServo.class, "Bucket");
//
//
//        FlipServo = hardwareMap.get(Servo.class, "FS");


    }

    @Override
    public void loop() {

        double sideuppos = SlideUP.getCurrentPosition();
        double slideoutpos = SLideOut.getCurrentPosition();
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


        if (gamepad2.dpad_left) {
            if(slideoutpos < 2693)
            {
                slideout = MAXOUTPOWER;
            }
            else {
                slideout = 0;
            }

        } else if (gamepad2.dpad_right) {
            if (slideoutpos > 500) {
                slideout = -MAXOUTPOWER;
            }

            else {
                slideout = 0;
            }
        } else {
            slideout = 0;
        }

        //***********************************************************************************************************







        if(gamepad2.dpad_up)
        {
            if(isP2SwichUp)
            {
                currentLevel = -1;

                if(P2currentLevel == -1)
                {
                    P2currentLevel = 0;
                }
                P2currentLevel++;
                isP2SwichUp = false;
            }

        }
        else if(!gamepad2.dpad_up)
        {
            isP2SwichUp = true;
        }

        if(P2currentLevel == 0)
        {
            error =  -sideuppos;
            slideup = (pidArm.calculatePIDAlgorithm(error));
            P2currentLevel=0;
        }

        else if(P2currentLevel == 1)
        {
            error = maxHight - sideuppos;
            slideup = (pidArm.calculatePIDAlgorithm(error));
            P2currentLevel=1;
        }

        else if(P2currentLevel >= 2)
        {
            P2currentLevel=0;
        }



        if(gamepad2.dpad_down)
        {
            isP2SwichUp = true;
            P2currentLevel = 0;

        }

//***********************************************************************************************************


//old
//        if(gamepad2.y)
//        {
//            if(enable)
//            {
//                position = !position;
//                if(position == true)
//                {
//                    FlipServo.setPosition(1);
//                }
//                if(position == false)
//                {
//                    FlipServo.setPosition(0);
//                }
//
//            }
//
//            enable = false;
//        }
//        else if(!gamepad2.y)
//        {
//            enable = true;
//        }


//***********************************************************************************************************




        //gamepad1 lift controls

        if(gamepad1.dpad_up)
        {

            if(isSwichUp) {
                P2currentLevel = -1;
                if(currentLevel <= -1)
                {
                    currentLevel=0;
                }
                currentLevel++;
                isSwichUp = false;
            }

        }
        else {
            isSwichUp = true;
        }



        if(currentLevel == 0)
        {
            error = -sideuppos;
            slideup = (pidArm.calculatePIDAlgorithm(error));
            currentLevel = 0;


        }


        else if(currentLevel == 1)
        {
            error = smallPole -sideuppos;
            slideup = (pidArm.calculatePIDAlgorithm(error));
            currentLevel = 1;
        }


        else if(currentLevel == 2)
        {
            error = tallPole - sideuppos;
            slideup = (pidArm.calculatePIDAlgorithm(error));
            currentLevel = 2;
        }


        else if(currentLevel >= 3)
        {
            currentLevel = 0;
        }


        if(gamepad1.dpad_down)
        {
            slideup = 0.3f;
            currentLevel = 0;
        }


//***********************************************************************************************************



//old

//
//        if(gamepad2.a)
//        {
//            if(position == true)
//            {
//                bucketServo.setPower(-1);
//            }
//            if(position == false)
//            {
//                bucketServo.setPower(1);
//            }
//
//        }
//
//        else if(gamepad2.options)
//        {
//            if(position == true)
//            {
//                bucketServo.setPower(1);
//            }
//            if(position == false)
//            {
//                bucketServo.setPower(-1);
//            }
//        }
//        else
//        {
//            bucketServo.setPower(0);
//        }




//***********************************************************************************************************

//old

//        if(gamepad2.right_bumper)
//        {
//            if(flipon)
//            {
//                flipposition = !flipposition;
//                if(flipposition == true)
//                {
//                    DumpServo.setPosition(0.5f);
//                }
//                if(flipposition == false)
//                {
//                    DumpServo.setPosition(0);
//                }
//
//            }
//
//            flipon = false;
//        }
//        else if(!gamepad2.right_bumper)
//        {
//            flipon = true;
//        }


//***********************************************************************************************************
        //reset encoder

        if(gamepad1.touchpad)
        {
            SlideUP.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            SlideUP.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }




//715

        if(gamepad1.right_bumper)
        {
            FLMotor.setPower(speeds[0] / 3); FRMotor.setPower(speeds[1] / 3); BLMotor.setPower(speeds[2] / 3);BRMotor.setPower(speeds[3] / 3);
        }
        else
        {
            FLMotor.setPower(speeds[0]); FRMotor.setPower(speeds[1]);BLMotor.setPower(speeds[2]); BRMotor.setPower(speeds[3]);
        }





//***********************************************************************************************************

        //old

//
//        if(sideuppos >= -490)
//        {
//            DumpServo.setPosition(0);
//        }

//        telemetry.addData("Slide out", slideout);
//        telemetry.addData("Slide up", slideup);
//        telemetry.addData("ArmPos",sideuppos);
//        telemetry.addData("OutPos", slideoutpos);

//
//        telemetry.addData("ERROR", error);
//
//        telemetry.addData("SERVOPOS", position);

        SlideUP.setPower(slideup);
        SLideOut.setPower(slideout);




        if (gamepad2.left_stick_y > 0.5) {
            SLideOut.setTargetPosition(HoriTransferPos);
            SLideOut.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            SLideOut.setPower(MAXOUTPOWER);

            //old
            //FlipServo.setPosition(1);
        }
        else {

            SLideOut.setDirection(DcMotorSimple.Direction.FORWARD);

            SLideOut.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

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

        //old
//        bucketServo.setPower(0);
//        FlipServo.setPosition(0);

    }
}