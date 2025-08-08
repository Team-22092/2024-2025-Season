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
public class Bucket {
    Servo bucket;
    HardwareMap hardwareMap;
    public Bucket(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        bucket = this.hardwareMap.get(Servo.class, "B");

    }

    public static double BUCKETSCOREPOS = 0.48;
    public static double BUCKETHOLDPOS = 0.71;

    //TODO - Add Color Sensor
    boolean setpos;
    public void Manual_Control(Gamepad gamepad, Gamepad Oldgamepad)
    {
        if(gamepad.right_bumper && !Oldgamepad.right_bumper)
        {

            setpos = !setpos;
            if(setpos)
            {
                bucket.setPosition(BUCKETSCOREPOS);
            }
            else{
                bucket.setPosition(BUCKETHOLDPOS);
            }

        }

    }
    public void display_telemetry(Telemetry telemetry) {




    }

    public void Setstart()
    {
        bucket.setPosition(BUCKETHOLDPOS);
    }

    public void dump()
    {
        bucket.setPosition(BUCKETSCOREPOS);
    }
    public void dumpBigger()
    {
        bucket.setPosition(BUCKETSCOREPOS - 0.03f);
    }

    public void BucketHoldPos()
    {
        bucket.setPosition(BUCKETHOLDPOS);
    }

    public class hold implements Action
    {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            bucket.setPosition(BUCKETSCOREPOS);
            return false;
        }
    }

//    public class dump implements Action
//    {
//
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//            bucket.setPosition(BUCKETHOLDPOS);
//            return false;
//        }
//    }
}
