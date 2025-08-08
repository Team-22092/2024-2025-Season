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
public class Extendo {

    boolean hasReset = false;
    HardwareMap hardwareMap;

    public static double LIFT_POWER = 0.0;

    public static int TransferPos = 238;
    public static int StartPos = 0;

    public static int MaxOutPos=2320;


    public int OUTTARGET = 0;


    //TODO - Add Touch Sensor
    TouchSensor touchSensor;
    public DcMotor extend;

    public Extendo(HardwareMap hardwareMap, boolean isauto) {
        this.hardwareMap = hardwareMap;
        //touchSensor = this.hardwareMap.get(TouchSensor.class, "Z");
        extend = this.hardwareMap.get(DcMotor.class, "H");

        extend.setDirection(DcMotorSimple.Direction.FORWARD);
        extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        if(!isauto)
        {

            extend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        else {
            extend.setTargetPosition(0);
            extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

       // extend.setTargetPosition(0);


    }

 public void setExtendTarget(int Target){

        extend.setTargetPosition(Target);
        extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extend.setPower(0.8);

    }

    boolean Lock;
    public void Manual_Control(Gamepad gamepad, Gamepad oldgamepad)
    {
        if(gamepad.dpad_left)
        {
            extend.setPower(-0.8);
            hasReset=false;


        }
        else if(gamepad.dpad_right && extend.getCurrentPosition() < MaxOutPos)
        {

                extend.setPower(1);

//            if(touchSensor.isPressed() && !hasReset)
//            {
//                extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//                extend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//                hasReset=true;
//            }
        }
        else {
            extend.setPower(0);
        }


        if(gamepad.touchpad && !oldgamepad.touchpad)
        {
            extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            extend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

//        else{
//            lift.setPower(0);
//        }
    }
    public void display_telemetry(Telemetry telemetry) {
        telemetry.addData("Lift pos: ", extend.getCurrentPosition());
       // telemetry.addData("touchsense: ", touchSensor.isPressed());
        //
        //telemetry.addData("Lift Target Position: ");
    }


    public class OutLoopAction implements Action
    {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            extend.setTargetPosition(OUTTARGET);
            extend.setPower(0.85);
            return true;
        }
    }


    public void setspeed(float speed)
    {
        extend.setPower(speed);
    }


    public float Getpos()
    {
        return extend.getCurrentPosition();
    }



}
