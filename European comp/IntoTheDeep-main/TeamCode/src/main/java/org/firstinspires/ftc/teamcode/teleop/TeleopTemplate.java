package org.firstinspires.ftc.teamcode.teleop;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Actions;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.TheThirdLeg;
import org.firstinspires.ftc.teamcode.hardware.Bucket;
import org.firstinspires.ftc.teamcode.hardware.Extendo;
import org.firstinspires.ftc.teamcode.hardware.Grabber;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Lift;
import org.firstinspires.ftc.teamcode.hardware.Wheels;

import java.util.List;


@TeleOp(name = "Skibity Sigma Ops")
public class TeleopTemplate extends OpMode {

    public class teletransfer implements Action
    { private double beginTs = -1;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            double t;
            if (beginTs < 0) {
                beginTs = Actions.now();
                t = 0;
            } else {
                t = Actions.now() - beginTs;
            }

            if (t >= 1.6) {
                extendo.extend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                return false;
            } else if (t >= 1.5) {
                intake.Left.setPower(0);
                intake.Right.setPower(0);
//                extendo.setExtendTarget(440);
            } else if (t >= 1) {
                intake.Left.setPower(-1);
                intake.Right.setPower(1);
            } else {
                extendo.setExtendTarget(232);
                intake.Flipdown();
            }
            return true;
        }
    }



    public enum STATE {
        MANUAL,
        AUTO_INTAKE,
    }
    private STATE currentState = STATE.MANUAL;
    private STATE previous_state = currentState;
    private boolean is_first_loop = true;

    Gamepad currentGamepadDrive = new Gamepad();
    Gamepad currentGamepadOther = new Gamepad();
    Gamepad prevGamepadDrive = new Gamepad();
    Gamepad prevGamepadOther = new Gamepad();

    Wheels wheels;
    //Lift lift;
    Intake intake;
    Grabber grabber;

    TheThirdLeg theThirdLeg;
    Lift lift;
    Action transfer;
    TelemetryPacket telemetryPacket;
    Bucket bucket;

    Extendo extendo;

    @Override
    public void init() {
        // DON'T MOVE HERE
        // BUT A GOOD SPOT TO INIT HARDWARE
        wheels = new Wheels(hardwareMap);
        intake = new Intake(hardwareMap);
        lift = new Lift(hardwareMap, intake);
        bucket = new Bucket(hardwareMap);

        grabber = new Grabber(hardwareMap);
        extendo = new Extendo(hardwareMap, false);
        theThirdLeg = new TheThirdLeg(hardwareMap);
        List<LynxModule> allhubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allhubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

    } // END OF INIT


    @Override
    public void start() {
        bucket.Setstart();
        intake.Flipmid();
        // RUNS ONCE ON START,

        try {
            Thread.sleep(1 * 1000);

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        lift.lift.setMode (DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        while (!lift.touchSensor.isPressed()) {
            lift.lift.setPower(-0.5);
        }
        lift.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.lift.setTargetPosition(0);
        lift.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // REMEMBER THAT THE ROBOT HAS JUST FINISHED AUTO



    } // END OF START


    @Override
    public void loop() {
        previous_state = currentState;
        prevGamepadDrive.copy(currentGamepadDrive);
        prevGamepadOther.copy(currentGamepadOther);
        currentGamepadDrive.copy(gamepad1);
        currentGamepadOther.copy(gamepad2);


        wheels.manual_drive(currentGamepadDrive);


        switch (currentState) {
            case MANUAL:
                lift.Manual_Control(currentGamepadOther, currentGamepadDrive, prevGamepadDrive, prevGamepadOther);
                bucket.Manual_Control(currentGamepadOther,prevGamepadOther);
                theThirdLeg.Manual_Control(currentGamepadOther,prevGamepadOther);
                grabber.Manual_Control(currentGamepadDrive, prevGamepadDrive);
                intake.Manual_Control(currentGamepadOther, prevGamepadOther);
                extendo.Manual_Control(currentGamepadOther, prevGamepadOther);
                if(currentGamepadOther.circle){
                    currentState = STATE.AUTO_INTAKE;
                    is_first_loop = true;
                }
                if (is_first_loop) {

                }
                break;
            case AUTO_INTAKE:
                // DO STUFF HERE
                if (is_first_loop) {
                    transfer = new teletransfer();
                   //flip bukcet and intake if there not in the right spot
//                    intake.Flipup();
//                    bucket.BucketHoldPos();
                    //set hori slide target pos and bring vert slide down
//                    extendo.setExtendTarget(Extendo.TransferPos);
                    is_first_loop = false;
                }
//               if (Math.abs(extendo.extend.getCurrentPosition() - Extendo.TransferPos) < 5){
//                  currentState = STATE.MANUAL;
//                  extendo.extend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//               }
                if(transfer.run(telemetryPacket) == false){
                    currentState = STATE.MANUAL;
                }
                break;
        }

        //TODO - Comment this out for competition
        lift.display_telemetry(telemetry);
        intake.display_telemetry(telemetry);
        extendo.display_telemetry(telemetry);

        telemetry.update();
        //TODO - End of telemetry

        is_first_loop = (currentState != previous_state);
    } // END OF LOOP
}
