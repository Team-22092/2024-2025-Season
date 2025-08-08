package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.PIDController;

import java.util.List;
@Disabled
@TeleOp(name = "Drive Code")
public class Drive extends OpMode {
    Gamepad currentGamepad1Drive = new Gamepad();
    Gamepad currentGamepad2Intake = new Gamepad();
    Gamepad PreviousGamepad1Drive = new Gamepad();
    Gamepad PreviousGamepad2Intake = new Gamepad();

    private DcMotor FLMotor, FRMotor, BRMotor, BLMotor;
    double[] speeds = new double[4];
    private double drive, strafe, turn, max;
    private double slow_mode_percent = 0.35;

    private DcMotor VertSlide;
    private double VertTallPole = 2049;
    private double VertMaxHeight = 4500;
    double kp = 0.0025, ki = 0.0, kd = 0.0;
    public PIDController pidArm = new PIDController(kp, ki, kd);
    double error;
    double VertCurrentPos;
    double VertDesiredPos = 0;

    private DcMotor HoriSlide;
    private double HoriPower = 1;
    private double HoriMaxExtendPos = 2693;
    private int HoriTransferPos = 600;
    private double HoriCurrentPos;

    private CRServo IntakeSpinServo;
    private double IntakeSpinIntakePower = 1;
    private double IntakeSpinTransferPower = -1;

    private Servo IntakeFlipServo;
    private double IntakeFServoDownPos = 0;
    private double IntakeFServoUpPos = 1;
    private double IntakeFlipCurrPos;

    private Servo OuttakeServo;
    private double OuttakeHoldPos = 0;
    private double OuttakeScorePos = 0.5;
    private double OuttakeCurrentPos;


    @Override
    public void init() {
        FLMotor = hardwareMap.get(DcMotor.class, "leftFront");
        FRMotor = hardwareMap.get(DcMotor.class, "rightFront");
        BRMotor = hardwareMap.get(DcMotor.class, "rightBack");
        BLMotor = hardwareMap.get(DcMotor.class, "leftBack");
        FLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        FRMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        BRMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        BLMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        VertSlide = hardwareMap.get(DcMotor.class, "Up");
        VertSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        VertSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        VertSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        HoriSlide = hardwareMap.get(DcMotor.class, "Out");
        HoriSlide.setDirection(DcMotorSimple.Direction.FORWARD);
        HoriSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        HoriSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        OuttakeServo = hardwareMap.get(Servo.class, "trayServo");
        IntakeSpinServo = hardwareMap.get(CRServo.class, "Bucket");
        IntakeFlipServo = hardwareMap.get(Servo.class, "FS");

        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }


        OuttakeServo.setPosition(0);


    }//end of init

    @Override
    public void init_loop() {
        //should be empty?
    }//end of init loop

    @Override
    public void start() {
        OuttakeServo.setPosition(OuttakeHoldPos);
        IntakeFlipServo.setPosition(IntakeFServoUpPos);
    }//end of start

    @Override
    public void loop() {

        PreviousGamepad1Drive.copy(currentGamepad1Drive);
        PreviousGamepad2Intake.copy(currentGamepad2Intake);
        currentGamepad1Drive.copy(gamepad1);
        currentGamepad2Intake.copy(gamepad2);

        VertCurrentPos = VertSlide.getCurrentPosition();
        HoriCurrentPos = HoriSlide.getCurrentPosition();
        IntakeFlipCurrPos = IntakeFlipServo.getPosition();
        OuttakeCurrentPos = OuttakeServo.getPosition();

        drive = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        // Set motor power
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
        if (currentGamepad1Drive.right_bumper) {
            FLMotor.setPower(speeds[0] * slow_mode_percent);
            FRMotor.setPower(speeds[1] * slow_mode_percent);
            BLMotor.setPower(speeds[2] * slow_mode_percent);
            BRMotor.setPower(speeds[3] * slow_mode_percent);
        } else {
            FLMotor.setPower(speeds[0]);
            FRMotor.setPower(speeds[1]);
            BLMotor.setPower(speeds[2]);
            BRMotor.setPower(speeds[3]);
        }

        if ((currentGamepad2Intake.dpad_left) && (HoriCurrentPos < HoriMaxExtendPos)) {
            HoriSlide.setPower(HoriPower); //slide out
        } else if ((currentGamepad2Intake.dpad_right) && (HoriCurrentPos > HoriTransferPos)) {
            HoriSlide.setPower(-HoriPower);
        } else {
            HoriSlide.setPower(0);
        }

        if (currentGamepad2Intake.triangle || currentGamepad1Drive.triangle) {
            VertDesiredPos = VertMaxHeight;
        } else if (currentGamepad2Intake.circle || currentGamepad1Drive.circle) {
            VertDesiredPos = VertTallPole;
        } else if (currentGamepad2Intake.cross || currentGamepad1Drive.cross) {
            VertDesiredPos = 0;
            OuttakeServo.setPosition(OuttakeHoldPos);
        }
        error = VertDesiredPos - VertCurrentPos;
        VertSlide.setPower(pidArm.calculatePIDAlgorithm(error));

        if (currentGamepad2Intake.left_bumper) {
            IntakeSpinServo.setPower(IntakeSpinIntakePower);
        } else if (currentGamepad2Intake.right_bumper) {
            IntakeSpinServo.setPower(IntakeSpinTransferPower);
        }
        else{
            IntakeSpinServo.setPower(0);
        }
        if (currentGamepad2Intake.left_trigger > 0.3) {
            IntakeFlipServo.setPosition(IntakeFServoDownPos);
        } else if (currentGamepad2Intake.right_trigger > 0.3) {
            IntakeFlipServo.setPosition(IntakeFServoUpPos);
        }


        if (currentGamepad2Intake.square && !PreviousGamepad2Intake.square) {
            if (OuttakeCurrentPos == OuttakeHoldPos){
                OuttakeServo.setPosition(OuttakeScorePos);
            } else {
                OuttakeServo.setPosition(OuttakeHoldPos);
            }}


        if (currentGamepad1Drive.square && !currentGamepad1Drive.square) {
            if (OuttakeCurrentPos == OuttakeHoldPos){
                OuttakeServo.setPosition(OuttakeScorePos);
            } else {
                OuttakeServo.setPosition(OuttakeHoldPos);
            }}

        if (currentGamepad2Intake.left_stick_y > 0.5) {
            HoriSlide.setTargetPosition(HoriTransferPos);
            HoriSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            HoriSlide.setPower(HoriPower);

            IntakeFlipServo.setPosition(IntakeFServoUpPos);

            if ((HoriCurrentPos == HoriTransferPos)) {
                IntakeSpinServo.setPower(IntakeSpinTransferPower);
            }
        } else if ((!(currentGamepad2Intake.left_stick_y > 0.5)) && (PreviousGamepad2Intake.left_stick_y > 0.5)){
            HoriSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        if(currentGamepad2Intake.touchpad && currentGamepad1Drive.touchpad) {
            VertSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            VertSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }


    }//end of loop

    @Override
    public void stop() {

        // Stop all motors if no input and if gamestop

        FLMotor.setPower(0);
        FRMotor.setPower(0);
        BRMotor.setPower(0);
        BLMotor.setPower(0);

        VertSlide.setPower(0);
        HoriSlide.setPower(0);
        IntakeSpinServo.setPower(0);

    }//end of stop
}//end of teleop