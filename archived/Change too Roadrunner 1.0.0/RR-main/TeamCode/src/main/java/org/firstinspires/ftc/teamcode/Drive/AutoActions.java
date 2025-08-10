package org.firstinspires.ftc.teamcode.Drive;





import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.Servo;


import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Actions;
import com.acmerobotics.roadrunner.InstantAction;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

public class AutoActions {
    DcMotor Lift;
    public Servo Flip;
    public Servo Claw;
    public DcMotor SLideOut;

    public CRServo IntakeLeft, IntakeRight;
    public Servo Intakeflip;

    public int LiftTargetPos= 0;
    public int SlideOutTargetPos = 360;
    public double IntakeFipTargetPos = 0.1;
    public double ClawTargetPos = 0.4;
    public double FlipTargetPos = 0;
    HardwareMap hardwareMap;
    public AutoActions(HardwareMap hardwareMap, boolean isauto)
    {
        this.hardwareMap = hardwareMap;
        //servos
        Claw = this.hardwareMap.get(Servo.class, "Claw");
        Flip = this.hardwareMap.get(Servo.class, "Flip");

        //Lift
        Lift  = hardwareMap.get(DcMotor.class, "Up");

        Lift.setDirection(DcMotorSimple.Direction.REVERSE);
        Lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Lift.setTargetPosition(0);
        Lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        SLideOut  = hardwareMap.get(DcMotor.class, "Out");

        SLideOut.setDirection(DcMotorSimple.Direction.FORWARD);
        SLideOut.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        if(isauto)
        {
            FlipTargetPos = 0;
            SLideOut.setTargetPosition(0);
            SLideOut.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }
        else{
            FlipTargetPos = 0.8;
            SLideOut.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }



        Intakeflip = hardwareMap.get(Servo.class, "IntakeFlip");


//        Claw = hardwareMap.get(Servo.class, "Claw");
//        Flip = hardwareMap.get(Servo.class, "Flip");

        //Cr servos
        IntakeLeft = hardwareMap.get(CRServo.class, "IntakeLeft");
        IntakeRight = hardwareMap.get(CRServo.class, "IntakeRight");
    }





    public class AllLoopAction implements Action{

        @Override
        public boolean run(@NonNull  TelemetryPacket p) {
            Lift.setTargetPosition(LiftTargetPos);
            Claw.setPosition(ClawTargetPos);
            Flip.setPosition(FlipTargetPos);
            Lift.setPower(1);
            return true;
        }
    }


    public void AllLoopFuction(){



        Lift.setTargetPosition(LiftTargetPos);
        Claw.setPosition(ClawTargetPos);
        Flip.setPosition(FlipTargetPos);
        Lift.setPower(0.7);


    }


    public class AllLoopFuctionSampleAuto implements Action{


        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            Lift.setTargetPosition(LiftTargetPos);
            Claw.setPosition(ClawTargetPos);
            Flip.setPosition(FlipTargetPos);
            SLideOut.setTargetPosition(SlideOutTargetPos);
            SLideOut.setPower(0.4);
            Lift.setPower(0.7);
            Intakeflip.setPosition(IntakeFipTargetPos);
            return true;
        }
    }



    int offset = 0;
    public class CHILL2SCORE implements Action{
        private double beginTs = -1;
        @Override
        public boolean run(@NonNull  TelemetryPacket p) {
            LiftTargetPos = 1120 + offset;
            double t;

            if (beginTs < 0) {
                beginTs = Actions.now();
                t = 0;
            } else {
                t = Actions.now() - beginTs;
            }


            if(Math.abs(Lift.getCurrentPosition() - LiftTargetPos) < 10)
            {
                ClawTargetPos = 0.7;
                if (t >= 1) {

                    offset = offset + 8;
                    return false;
                }
            }
            return true;
        }
    }



    public class SCORE2CHILL implements Action{
        private double beginTs = -1;
        @Override
        public boolean run(@NonNull  TelemetryPacket p) {
            double t;
            if (beginTs < 0) {
                beginTs = Actions.now();
                t = 0;
            } else {
                t = Actions.now() - beginTs;
            }
            ClawTargetPos = 0.2;

            if (t >= 2) {
                ClawTargetPos = 0.7;

                return false;
            }

            return true;
        }
    }





    public class IntakeAuto implements Action{
        private boolean targ1reached = false;
        private boolean init = false;
        private Action Outakeaction;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(!init)
            {
                SLideOut.setTargetPosition(400);

                SLideOut.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                SLideOut.setPower(0.5);

                Intakeflip.setPosition(0.9);
                init = true;
                Outakeaction = new Outtake();



            }
            else{
                SLideOut.setPower(0.5);
            }
            if(Math.abs(SLideOut.getCurrentPosition() - 400) < 20 && !targ1reached){

                if(!Outakeaction.run(telemetryPacket))
                {

                    targ1reached = true;

                }

            }

            if(targ1reached)
            {
                SLideOut.setTargetPosition(800);
                if(Math.abs(SLideOut.getCurrentPosition() - 800) < 20){
                    SLideOut.setPower(0);
                    SLideOut.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                    return false;
                }

            }


            return true;
        }
    }

















    public class IntakeAutoAuto implements Action{
        private boolean targ1reached = false;
        private boolean init = false;
        private Action Outakeaction;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(!init)
            {
                SlideOutTargetPos = 400;

//                SLideOut.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                SLideOut.setPower(0.5);

                IntakeFipTargetPos = 0.9;
                init = true;                Outakeaction = new Outtake();



            }
//            else{
//                SLideOut.setPower(0.5);
//            }
            if(Math.abs(SLideOut.getCurrentPosition() - 400) < 20 && !targ1reached){

                if(!Outakeaction.run(telemetryPacket))
                {

                    targ1reached = true;

                }

            }

            if(targ1reached)
            {

                SlideOutTargetPos = 800;
                if(Math.abs(SLideOut.getCurrentPosition() - 800) < 20){
                    return false;
                }

            }


            return true;
        }
    }




    public class Outtake implements Action{


        private double beginTs = -1;
        @Override
        public boolean run(@NonNull  TelemetryPacket p) {
            double t;
            if (beginTs < 0) {
                beginTs = Actions.now();
                t = 0;
            } else {
                t = Actions.now() - beginTs;
            }
            if(t <=1)
            {
                IntakeRight.setPower(-1); IntakeLeft.setPower(1);
            }
            else{
                IntakeRight.setPower(0); IntakeLeft.setPower(0);
                return false;
            }

            return true;
        }
    }




    public class Intake implements Action{


        private double beginTs = -1;
        @Override
        public boolean run(@NonNull  TelemetryPacket p) {
            double t;
            if (beginTs < 0) {
                beginTs = Actions.now();
                t = 0;
            } else {
                t = Actions.now() - beginTs;
            }
            if(t <=3)
            {
               // SlideOutTargetPos = SlideOutTargetPos + 13;
                IntakeRight.setPower(1); IntakeLeft.setPower(-1);
            }
            else{
                IntakeRight.setPower(0); IntakeLeft.setPower(0);
                return false;
            }

            return true;
        }
    }


    public class SampleChill implements Action{

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

          //  SetLiftTarget(1500+offset);
            SetLiftTarget(0);
            SetFlipTarget(0.4);
            FlipTargetPos = 0.4;

            if(Math.abs(Lift.getCurrentPosition() - (0)) < 100)
            {

                SetClawTarget(0.2);
                return false;
            }
            return true;
        }
    }

    public Action Samplechillhelper() {return  new SampleChill();}
  public Action LoopAuto() { return  new AllLoopFuctionSampleAuto();}

    public Action IntakeAuto()
    {
        return new IntakeAutoAuto();
    }
    public Action Intake2SCORE(){
        return new IntakeAuto();
    }

    public Action AutoTransfer(){
        return new Intake();
    }

    public Action SetSlideOutTargetPos(int target_pos)
    {
        return new InstantAction(() -> SlideOutTargetPos = target_pos);
    }

    public Action SetFlipTargetPos(double target_pos)
    {
        return new InstantAction(() -> IntakeFipTargetPos = target_pos);
    }

    public Action chill2Score(){return new CHILL2SCORE();}

    public Action SCORE2CHILL(){
        return new SCORE2CHILL();
    }


    public Action LiftLoop(){
        return new AllLoopAction();
    }

    public Action SetliftTargetAction(int target_pos){

        return new InstantAction(() -> LiftTargetPos = target_pos);
    }

    public void SetLiftTarget(int target_pos){

        LiftTargetPos = target_pos;
    }

    public Action SetClawTargetAction(double target_pos){

        return new InstantAction(() -> ClawTargetPos = target_pos);
    }

    public void SetClawTarget(double target_pos){

        ClawTargetPos = target_pos;
    }

    public Action SetFlipTargetAction(double target_pos){

        return new InstantAction(() -> FlipTargetPos = target_pos);
    }

    public void SetFlipTarget(double target_pos){

        FlipTargetPos = target_pos;
    }




}