package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Lift {

    HardwareMap hardwareMap;

    public static double LIFT_POWER = 0.0;

    public static int LIFT_SAMPLE_CATCH_HEIGHT = 0;
    public static int LIFT_SAMPLE_SCORE_HEIGHT = 3000;
    Intake intake;


    public int LIFTTARGET = 0;
    public static int LIFT_SPECIMEN_GRAB_HEIGHT = 0;
    public static int LIFT_SPECIMEN_SCORE_HEIGHT = 0;

    //TODO - Add Touch Sensor
    public TouchSensor touchSensor;
    public DcMotor lift;

    public Lift(HardwareMap hardwareMap, Intake intake) {
        this.hardwareMap = hardwareMap;
        touchSensor = this.hardwareMap.get(TouchSensor.class, "VertSen");
        lift = this.hardwareMap.get(DcMotor.class, "L");

        this.intake = intake;

        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setTargetPosition(0);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }
    boolean lifttoggle = false;
    boolean resetlock = false;
    public void Manual_Control(Gamepad gamepad2, Gamepad gamepadSpecimin, Gamepad oldgamepadSpecimin, Gamepad gamepad2old)
    {
        if(gamepadSpecimin.right_trigger > 0.5 && !(oldgamepadSpecimin.right_trigger > 0.5))
        {
            lift.setPower(1);
            lifttoggle =! lifttoggle;
            if(lifttoggle)
            {
                lift.setTargetPosition(1400);

            }
            else{
                lift.setTargetPosition(0);
            }

            if(touchSensor.isPressed() && !resetlock)
            {
                lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                resetlock=true;
            }
            else{
                resetlock=false;
            }

        }

        if(gamepad2.dpad_up && !gamepad2old.dpad_up)
        { this.intake.Flipmid();
            lift.setPower(1);
            lifttoggle =! lifttoggle;
            if(lifttoggle)
            {
                lift.setTargetPosition(LIFT_SAMPLE_SCORE_HEIGHT);

            }
            else{
                lift.setTargetPosition(0);
            }

            if(touchSensor.isPressed() && !resetlock)
            {
                lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                resetlock=true;
            }
            else{
                resetlock=false;
            }

        }


        if(touchSensor.isPressed() && lift.getTargetPosition() == 0)
        {
            lift.setPower(0);
        }



    }
    public void display_telemetry(Telemetry telemetry) {
        telemetry.addData("Lift pos: ", lift.getCurrentPosition());
        telemetry.addData("Lift power: ", lift.getPower());
        telemetry.addData("Lift Press: ", touchSensor.isPressed());
        telemetry.addData("LiftTargetPos:", lift.getTargetPosition());
        //telemetry.addData("Lift Target Position: ");
    }


    public class LiftLoopActionSample implements Action
    {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            lift.setTargetPosition(LIFTTARGET);
            lift.setPower(1);

            return true;
        }
    }

    public class LiftLoopActionSpec implements Action
    {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            lift.setTargetPosition(LIFTTARGET);
            lift.setPower(1);

            return true;
        }
    }

    public float Getpos()
    {
        return lift.getCurrentPosition();
    }




}