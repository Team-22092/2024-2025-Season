package org.firstinspires.ftc.teamcode.Drive;

import android.database.CrossProcessCursor;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import org.firstinspires.ftc.teamcode.PIDController;
import com.qualcomm.robotcore.hardware.LED;
import java.util.List;
import java.util.Timer;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "AlphaDrive")
public class AlphaDrive extends OpMode {
    AutoActions AA;

    public int RobotState = 0;
    //SAMPLECHILL = 0, CHILL2SCORE = 1, SAMPLESCORE = 2, SCORE2CHILL = 3


    Action Intakeaction;









    //LED
    private DigitalChannel LEDLED;
    //COLOR SENSOR
  //  ColorSensor COLOR;

    Gamepad CurrentGampadDrive = new Gamepad();
    Gamepad CurrentGampadIntake = new Gamepad();


    Gamepad PrevGampadDrive = new Gamepad();
    Gamepad PrevGampadIntake = new Gamepad();


    private DcMotor FLMotor, FRMotor, BRMotor, BLMotor;
    boolean isSwichUp = true;

    double open = 0.2;
    double close = 0.7;

    double grabpos = 0.94;
    double droppos = 0.0;




    public boolean intakepos;




    //speeds
    double[] speeds = new double[4];
    private double drive, strafe, turn, max;

    private double slow_mode_precent = 0.333;

//    //VERT SLIDES STUFF
//    private DcMotor SlideUP;
//


    private int SpeciminPickupHight = 2049;
    private int SpeciminScoreHight = 2049;




    private int SampleBucket = 4150;
    private int SamplePickup = 1111;


    double Horizontalcurrentpos;
    double VertDesiredPos = 0;

    //tall
    private int currentLevel = 0;
    private int P2currentLevel = -1;

    private double maxHight = -4500;
    private double tallPole = -2049;
    private double smallPole = -929;

    private boolean isautotransfer = false;

    double slideup;


    //OUT SLIDES STUFF

    private double SLideOutPower = 0.7;
    private double IntakeInPos;
    private double MaxSlideOut;
    private double SlideOutCurrentPos;

    double kp = 0.0025, ki = 0.0, kd = 0.000;
    public PIDController pidArm = new PIDController(kp, ki ,kd);

    double error;






//    //Claw And Pivot
//    public Servo Claw;
//    public Servo Flip;





    Action liftloop;

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



        //LED
        LEDLED = hardwareMap.get(DigitalChannel.class, "LED");
        //COLOR SENSOR
      //  COLOR = hardwareMap.get(ColorSensor.class, "COLOR");


//        SlideUP  = hardwareMap.get(DcMotor.class, "Up");
//        SlideUP.setDirection(DcMotorSimple.Direction.REVERSE);
//        SlideUP.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        SlideUP.setTargetPosition(0);
//        SlideUP.setMode(DcMotor.RunMode.RUN_TO_POSITION);

//





        LEDLED.setMode(DigitalChannel.Mode.OUTPUT);
        //IMU


        LEDLED.setState(true);

        List<LynxModule> allhubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub: allhubs)
        {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }


        AA = new AutoActions(hardwareMap, false);
        liftloop = AA.LiftLoop();


    }//end of init


    @Override
    public void init_loop(){
        //empty
    }//end of init loop


    @Override
    public void start(){
        AA.Flip.setPosition(0.8);



        //empty
    }//end of start


    boolean intake = true;
    int oldalpha;



    double ClawTargetPos = close;
    double ArmTargetPos = 0.3;
    boolean isProperDown = false;
    boolean ManualMode = false;

    int targetPos = 0;

    boolean isFirstLoop = false;
    //set pos
    ElapsedTime Timer = new ElapsedTime();

    boolean ArmGoingDown = false;
    Action currentAction;
    @Override
    public void loop(){
        //telem
        TelemetryPacket packet = new TelemetryPacket();



        Horizontalcurrentpos = AA.SLideOut.getCurrentPosition();








        PrevGampadDrive.copy(CurrentGampadDrive);
        PrevGampadIntake.copy(CurrentGampadIntake);

        CurrentGampadDrive.copy(gamepad1);
        CurrentGampadIntake.copy(gamepad2);

//        VertCurrentPos = SlideUP.getCurrentPosition();
     //   SlideOutCurrentPos = SLideOut.getCurrentPosition();

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
//        if(CurrentGampadIntake.a && intake)
//        {
//            IntakeRight.setPower(1); IntakeLeft.setPower(-1);
//
//        }
//        else if (CurrentGampadIntake.a && !intake){
//            IntakeRight.setPower(-1); IntakeLeft.setPower(1);
//        }
//        else{
//            IntakeRight.setPower(0); IntakeLeft.setPower(0);
//        }
//        if (CurrentGampadIntake.options)
//        {
//            IntakeRight.setPower(-1); IntakeLeft.setPower(1);
//        }





        //out pivot
//        if (CurrentGampadIntake.x && !PrevGampadIntake.x) {
//
//            if(ArmTargetPos == grabpos || ArmTargetPos == 0.3)
//            {
//
//                ArmTargetPos = droppos;
//                ClawTargetPos = close;
//            }
//            else {
//                ClawTargetPos = close;
//                ArmTargetPos = grabpos;
//
//                Timer.reset();
//                ArmGoingDown = true;
//
//            }
//
//        }



//        if (CurrentGampadDrive.x && !CurrentGampadDrive.x) {
//
//        }

//        if(gamepad2.x)
//        {
//            claw = false;
//            if(flippos) {
//
//                if(currentclawpos <= -1)
//                {
//                    currentclawpos=0;
//                }
//                Claw.setPosition(0.65);
//
//
//                currentclawpos++;
//                flippos = false;
//
//            }
//
//        }
//        else {
//            flippos = true;
//        }


//
//        if(CurrentGampadIntake.b && !PrevGampadIntake.b)
//        {
//            intakepos = !intakepos;
//
//            //alpha build one skibity toilet ohio
//        }
//        int alphaDiff = Math.abs(COLOR.alpha() - oldalpha);
//
//        if(intakepos)
//        {
//            Intakeflip.setPosition(0.9);
//            intake = false;
//
//            if (alphaDiff > 20) {
//                LEDLED.setState(!LEDLED.getState());
//                oldalpha = COLOR.alpha();
//            }
//        }
//        else
//        {
//            intake = true;
//
//            Intakeflip.setPosition(0.25f);
//            if (alphaDiff > 20) {
//                LEDLED.setState(!LEDLED.getState());
//                oldalpha = COLOR.alpha();
//            }
//        }
//
//
//        if (alphaDiff > 20) {
//            LEDLED.setState(!LEDLED.getState());
//            oldalpha = COLOR.alpha();
//        }


        //horizontal slide
        if (CurrentGampadIntake.left_stick_y > 0.43 && Horizontalcurrentpos > 0) {

            AA.SLideOut.setPower(-SLideOutPower);
        }
        else if (CurrentGampadIntake.left_stick_y < -0.43 && Horizontalcurrentpos < 1828)
        {
            AA.SLideOut.setPower(SLideOutPower);
        }
        else
        {
            AA.SLideOut.setPower(0);
        }

        if(CurrentGampadIntake.right_bumper && !PrevGampadIntake.right_bumper)
        {
            if(intake)
            {
                AA.Intakeflip.setPosition(0.9);
            }
            else {
                AA.Intakeflip.setPosition(0.25f);
            }

            intake = !intake;
        }
        if(CurrentGampadIntake.cross)
        {
            AA.IntakeRight.setPower(1); AA.IntakeLeft.setPower(-1);

        }
        else if (CurrentGampadIntake.options){
            AA.IntakeRight.setPower(-1); AA.IntakeLeft.setPower(1);
        }
        else{
            AA.IntakeRight.setPower(0); AA.IntakeLeft.setPower(0);
        }





///////////////////////////////////////////////////////////////////////////

//        if(gamepad1.dpad_up)
//        {
//            claw = true;
//            if(isSwichUp) {
//                claw = true;
//                P2currentLevel = -1;
//                if(currentLevel <= -1)
//                {
//
//                    currentLevel=0;
//                    claw = true;
//                }
//                claw = true;
//
//
//                currentLevel++;
//                isSwichUp = false;
//            }
//
//        }
//        else {
//            isSwichUp = true;
//        }
//
//
//
//        if(currentLevel == 0)
//        {
//
//
////            if(claw)
////            {
////                Claw.setPosition(close);
////                currentclawpos = 1;
////            }
////            currentLevel = 0;
////
////
//
//
//
//
//
//            error =  -sideuppos;
//            slideup = (pidArm.calculatePIDAlgorithm(error));
//
////
////            if(SlideUP.getCurrentPosition() >= -100 && !isProperDown){
////                Claw.setPosition(open);
////                currentclawpos = 1;
////                SlideUP.setPower(0);
////
////
////                //wait a bit
////                try {
////                    Thread.sleep(100);
////
////                } catch (InterruptedException e) {
////                    throw new RuntimeException(e);
////                }
////
////                //reset
////                SlideUP.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
////                SlideUP.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
////                isProperDown = true;
////
////            }
////
////
////            if(isProperDown)
////            {
////
////                error = -1200 -sideuppos;
////                slideup = (pidArm.calculatePIDAlgorithm(error));
////
////                //repeat parts
////                if(SlideUP.getCurrentPosition() <= -1100){
////                    if(claw)
////                    {
////
////                        Claw.setPosition(open);
////                        currentclawpos = 0;
////
////                        claw = false;
////                    }
////
////                }
////
////                //single set
////                if(SlideUP.getCurrentPosition() <= -1200){
////                    currentclawpos = 0;
////
////                    isProperDown = false;
////                }
////            }
//
//
//        }
//
//        if(CurrentGampadIntake.right_bumper && !PrevGampadIntake.right_bumper)
//        {
//            if(ClawTargetPos == open)
//            {
//                ClawTargetPos = close;
//            }
//            else {
//                ClawTargetPos = open;
//            }
//        }
//
//
//
//
//        else if(currentLevel == 1)
//        {
//            error = tallPole - sideuppos;
//            slideup = (pidArm.calculatePIDAlgorithm(error));
//            currentLevel = 2;
//        }
//
//
//        else if(currentLevel >= 2)
//        {
//            currentLevel = 0;
//        }
//
//
//        if(gamepad1.dpad_down)
//        {
//            slideup = 0.3f;
//            currentLevel = 0;
//        }

//        if(CurrentGampadIntake.share && !PrevGampadIntake.share)
//        {
//            ManualMode = !ManualMode;
//        }
//        if(!ManualMode)
//        {
//            if(CurrentGampadIntake.dpad_down && !PrevGampadIntake.dpad_down)
//            {
//                targetPos = SamplePickup;
//            }
//            else if(CurrentGampadIntake.dpad_up && !PrevGampadIntake.dpad_up)
//            {
//                targetPos = SampleBucket;
//            }
//        }
//        else{
//            if(CurrentGampadIntake.dpad_down )
//            {
//                targetPos = targetPos - 5; //flipped
//            }
//            else if(CurrentGampadIntake.dpad_up)
//            {
//                targetPos = targetPos + 5;
//            }
//        }
        if(CurrentGampadIntake.circle && !PrevGampadIntake.circle)
        {
            isautotransfer = true;
            Intakeaction = AA.Intake2SCORE();
        }

        if(isautotransfer)
        {
            //this is outtake not intake
            if(!Intakeaction.run(packet))
            {
                isautotransfer = false;
            }

        }

        switch (RobotState){
            //SAMPLECHILL
            case 0:

                if(CurrentGampadIntake.dpad_down && !PrevGampadIntake.dpad_down)
                {
                    RobotState = 1;
                    isFirstLoop = true;
                }
                AA.SetLiftTarget(1500+AA.offset);

                if(Math.abs(AA.Lift.getCurrentPosition() - (1500 + AA.offset)) < 100)
                {
                    AA.SetFlipTarget(grabpos);
                    AA.SetClawTarget(open);
                }

                break;

            //CHILL2SCORE
            case 1:

                if(isFirstLoop)
                {
                    currentAction = AA.chill2Score();
                    isFirstLoop = false;

                }
                else if (!currentAction.run(packet)){
                    RobotState = 2;

                }





                break;
            //SAMPLESCORE
            case 2:
                if(CurrentGampadIntake.dpad_up && !PrevGampadIntake.dpad_up)
                {
                    RobotState = 3;
                    isFirstLoop = true;
                }

                AA.SetLiftTarget(SampleBucket + AA.offset);
                AA.SetFlipTarget(droppos);
                AA.SetClawTarget(close);


                break;
            //SCORE2CHILL
            case 3:
                if(isFirstLoop)
                {
                    currentAction = AA.SCORE2CHILL();
                    isFirstLoop = false;

                }
                else if (!currentAction.run(packet)){
                    RobotState = 0;

                }
                break;

            case 4:
                if(CurrentGampadIntake.dpad_up)
                {
                    AA.LiftTargetPos = AA.LiftTargetPos + 40;
                }
                if(CurrentGampadIntake.dpad_down)
                {
                    AA.LiftTargetPos = AA.LiftTargetPos - 40;
                }
                break;



        }

        AA.AllLoopFuction();


//        SlideUP.setPower(0.5);
//        SlideUP.setTargetPosition(targetPos);
//        if(Timer.seconds() > 0.5f && ArmGoingDown)
//        {
//            ClawTargetPos = open;
//            ArmGoingDown = false;
//
//        }
//
//        Claw.setPosition(ClawTargetPos);
//        Flip.setPosition(ArmTargetPos);




























////////////////////////////////////////////////////////////////////////////




//        telemetry.addData("FlipPos", Intakeflip.getPosition());
//
//        telemetry.addData("intakepos", intakepos);

//        telemetry.addData("ManualMode", ManualMode);
//
//        telemetry.addData("FlipCurrentPos", ArmTargetPos);
//        telemetry.addData("Robot state", RobotState);
//        if(ClawTargetPos == open)
//        {
//            telemetry.addLine("Claw Is Open");
//        }
//        else{
//            telemetry.addLine("Claw Is Closed");
//        }
        telemetry.addData("OUtslide", AA.SLideOut.getCurrentPosition());


        telemetry.update();



    }//end of loop


    @Override
    public void stop()
    {

        //  LEDLED.setState(false);

        FLMotor.setPower(0);
        FRMotor.setPower(0);
        BLMotor.setPower(0);
        BRMotor.setPower(0);


    }//end of stop

}//emd pf teleop