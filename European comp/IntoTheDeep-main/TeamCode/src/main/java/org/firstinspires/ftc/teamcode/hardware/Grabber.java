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
public class Grabber {
    Servo grabber;
    HardwareMap hardwareMap;
    public Grabber(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        grabber = this.hardwareMap.get(Servo.class, "G");

    }

    public static double GRABBEROPEN = 0;
    public static double GRABBERCLOSE = 0.29;

    //TODO - Add Color Sensor
    public boolean old;
    public void Manual_Control(Gamepad gamepad, Gamepad Oldgamepad)
    {

        if(gamepad.left_bumper && !Oldgamepad.left_bumper)
        {

            old = !old;
            if(old)
            {
                grabber.setPosition(GRABBEROPEN);
            }
            else{
                grabber.setPosition(GRABBERCLOSE);
            }

        }

    }
    public void display_telemetry(Telemetry telemetry) {




    }


    public class open implements Action
    {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            grabber.setPosition(GRABBEROPEN);
            return false;
        }
    }

    public class close implements Action
    {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            grabber.setPosition(GRABBERCLOSE);
            return false;
        }
    }

    public void Startclose()
    {


            grabber.setPosition(GRABBERCLOSE);


    }
}
