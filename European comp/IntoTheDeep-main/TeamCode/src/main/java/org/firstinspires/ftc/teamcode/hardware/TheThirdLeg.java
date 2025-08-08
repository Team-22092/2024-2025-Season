package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class TheThirdLeg {
    Servo flick;
  //  Servo lebronreamonjames;
    HardwareMap hardwareMap;
    public TheThirdLeg(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        flick = this.hardwareMap.get(Servo.class, "flick");
       // lebronreamonjames = this.hardwareMap.get(Servo.class, "ops");

    }

    public static double flickout = 1;

    public static double flickin = 0.3;

    //TODO - Add Color Sensor
    boolean setpos;
    public void Manual_Control(Gamepad gamepad, Gamepad Oldgamepad)
    {
        if(gamepad.left_bumper && !Oldgamepad.left_bumper)
        {

            setpos = !setpos;
            if(setpos)
            {
                flick.setPosition(flickout);
            }
            else{
                flick.setPosition(flickin);
            }

        }

//        if(gamepad.dpad_down && Oldgamepad.dpad_down)
//        {
//            lebrontest = !lebrontest;
//            if(lebrontest)
//            {
//                lebronreamonjames.setPosition(BUCKETSCOREPOS);
//            }
//            else{
//                lebronreamonjames.setPosition(BUCKETHOLDPOS);
//            }
//        }
    }

    public void Setstart()
    {
        flick.setPosition(flickin);
    }

    public void Hitout(float pos)
    {
        flick.setPosition(pos);
    }

//    public class HitoutAction implements Action
//    {
//
//
//
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//            flick.setPosition(BUCKETHOLDPOS);
//            return false;
//        }
//    }
}
