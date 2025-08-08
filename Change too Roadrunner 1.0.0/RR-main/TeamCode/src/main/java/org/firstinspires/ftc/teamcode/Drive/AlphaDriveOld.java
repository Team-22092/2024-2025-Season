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
@TeleOp(name = "AlphaDriveOLD")
public class AlphaDriveOld extends OpMode {
    Gamepad CurrentGampadDrive = new Gamepad();
    Gamepad CurrentGampadIntake = new Gamepad();


    Gamepad PrevGampadDrive = new Gamepad();
    Gamepad PrevGampadIntake = new Gamepad();


    private DcMotor FLMotor, FRMotor, BRMotor, BLMotor;
    boolean isSwichUp = true;



    private CRServo IntakeLeft, IntakeRight;
    private Servo Intakeflip;
    public boolean intakepos;




    //speeds
    double[] speeds = new double[4];
    private double drive, strafe, turn, max;

    private double slow_mode_precent = 0.333;

    //VERT SLIDES STUFF
    private DcMotor SlideUP;

    private double BucketHight = 4500;
    private double SpeciminScoreHight = 2049;

    private double TransferHight = 2049;
    private double SpeciminPickupHight = 2049;

    double VertCurrentPos;
    double VertDesiredPos = 0;

    //tall
    private int currentLevel = -1;
    private int P2currentLevel = -1;

    private double maxHight = -4500;
    private double tallPole = -2049;
    private double smallPole = -929;



    double slideup;


    //OUT SLIDES STUFF
    private DcMotor SLideOut;
    private double SLideOutPower = 0.6;
    private double IntakeInPos;
    private double MaxSlideOut;
    private double SlideOutCurrentPos;

    double kp = 0.0025, ki = 0.0, kd = 0.000;
    public PIDController pidArm = new PIDController(kp, ki ,kd);

    double error;

    @Override
    public void init()
    {
        telemetry.addLine(">> Welcome :)");
        telemetry.update();


        FLMotor  = hardwareMap.get(DcMotor.class, "leftFront");
        FRMotor  = hardwareMap.get(DcMotor.class, "rightFront");
        BRMotor  = hardwareMap.get(DcMotor.class, "rightBack");
        BLMotor  = hardwareMap.get(DcMotor.class, "leftBack");

        FLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        FRMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        BRMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        BLMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //normal intake servo
        Intakeflip = hardwareMap.get(Servo.class, "IntakeFlip");

        //Cr servos
        IntakeLeft = hardwareMap.get(CRServo.class, "IntakeLeft");
        IntakeRight = hardwareMap.get(CRServo.class, "IntakeRight");






        SlideUP  = hardwareMap.get(DcMotor.class, "Up");
        SlideUP.setDirection(DcMotorSimple.Direction.FORWARD);
        SlideUP.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SlideUP.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

//
        SLideOut  = hardwareMap.get(DcMotor.class, "Out");
        SLideOut.setDirection(DcMotorSimple.Direction.FORWARD);
        SLideOut.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SLideOut.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        List<LynxModule> allhubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub: allhubs)
        {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

    }//end of init


    @Override
    public void init_loop(){
        //empty
    }//end of init loop


    @Override
    public void start(){
        //empty
    }//end of start


    boolean intake = true;
    @Override
    public void loop(){

        double sideuppos = SlideUP.getCurrentPosition();





        PrevGampadDrive.copy(CurrentGampadDrive);
        PrevGampadIntake.copy(CurrentGampadIntake);

        CurrentGampadDrive.copy(gamepad1);
        CurrentGampadIntake.copy(gamepad2);

//        VertCurrentPos = SlideUP.getCurrentPosition();
        SlideOutCurrentPos = SLideOut.getCurrentPosition();

        drive = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;


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


        if(CurrentGampadDrive.right_bumper){
            FLMotor.setPower(speeds[0] * slow_mode_precent);
            FRMotor.setPower(speeds[1] * slow_mode_precent);
            BLMotor.setPower(speeds[2] * slow_mode_precent);
            BRMotor.setPower(speeds[3] * slow_mode_precent);
        }else{
            FLMotor.setPower(speeds[0]);
            FRMotor.setPower(speeds[1]);
            BLMotor.setPower(speeds[2]);
            BRMotor.setPower(speeds[3]);
        }






        //servo
        if(CurrentGampadIntake.a && intake)
        {
            IntakeLeft.setPower(1); IntakeRight.setPower(-1);

        }
        else if (CurrentGampadIntake.a && !intake){
            IntakeLeft.setPower(-1); IntakeRight.setPower(1);
        }
        else{
            IntakeLeft.setPower(0); IntakeRight.setPower(0);
        }
        if (CurrentGampadIntake.options)
        {
            IntakeLeft.setPower(-1); IntakeRight.setPower(1);
        }





        if(CurrentGampadIntake.right_bumper && !PrevGampadIntake.right_bumper)
        {
            intakepos = !intakepos;

            //alpha build one skibity toilet ohio
        }

        if(intakepos)
        {
            Intakeflip.setPosition(0.8);
            intake = false;
        }
        else
        {
            intake = true;

            Intakeflip.setPosition(0.29f);
        }





        //horizontal slide
        if (CurrentGampadIntake.dpad_right) {

            SLideOut.setPower(SLideOutPower);
        }
        else if (CurrentGampadIntake.dpad_left)
        {
            SLideOut.setPower(-SLideOutPower);
        }
        else
        {
            SLideOut.setPower(0);
        }







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
            error =  -sideuppos;
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



        SlideUP.setPower(slideup);








        telemetry.addData("FlipPos", Intakeflip.getPosition());

        telemetry.addData("currentlevel", currentLevel);
        telemetry.addData("intakepos", intakepos);
        telemetry.addData("up", sideuppos);


        telemetry.update();
    }//end of loop


    @Override
    public void stop()
    {
        FLMotor.setPower(0);
        FRMotor.setPower(0);
        BLMotor.setPower(0);
        BRMotor.setPower(0);


    }//end of stop

}//emd pf teleop
