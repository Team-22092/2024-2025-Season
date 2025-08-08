package org.firstinspires.ftc.teamcode.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Intake {

        Servo Intakeflip;
        public CRServo Left, Right;
//    ColorSensor color;
//
//    //Lights
//    private DigitalChannel redLED;
//    private DigitalChannel greenLED;


    HardwareMap hardwareMap;
    public Intake(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
        Intakeflip = hardwareMap.get(Servo.class, "flip");
        Left = hardwareMap.get(CRServo.class, "Right");
        Right =hardwareMap.get(CRServo.class, "Left");

//        redLED = hardwareMap.get(DigitalChannel.class, "red");
//        greenLED = hardwareMap.get(DigitalChannel.class, "green");
//        color = this.hardwareMap.get(ColorSensor.class, "Color");
    }

    public static double INTAKE_FLIP_DOWN_POSITION = 0.1;
    public static double INTAKE_FLIP_UP_POSITION = 0.8;
    public static double Intake_Flip_Middle_Position = 0.35;

    //TODO - Add Color Sensor
    public static boolean flip;
    public void Manual_Control(Gamepad gamepad, Gamepad prevGamepadOther)
    {

         if(gamepad.cross)
        {

            if(flip)
            {
                Left.setPower(1);
                Right.setPower(-1);
            }
            else
            {
                Left.setPower(-1);
                Right.setPower(1);

            }

        }
         else {
             Left.setPower(0);
             Right.setPower(0);
         }
         if(gamepad.options)
         {
             Left.setPower(-1);
             Right.setPower(1);
         }

        if(gamepad.triangle  && !prevGamepadOther.triangle)
        {
            if(flip)
            {
                Flipdown();
            }
            else{
                Flipup();
            }

        }
    }

    public void display_telemetry(Telemetry telemetry) {
        telemetry.addData("Flip", flip);

//        telemetry.addData("Red", color.red());
//        telemetry.addData("Green", color.green());
//        telemetry.addData("Blue", color.blue());
//
//        telemetry.addData("Alpha", color.alpha());
//        telemetry.addData("RGB", color.argb());
//

    }
    //2120
    public void Flipup()
    {
        Intakeflip.setPosition(INTAKE_FLIP_UP_POSITION);
        flip = true;
    }

    public void Flipmid()
    {
        Intakeflip.setPosition(Intake_Flip_Middle_Position);
        flip = true;
    }

    public void Flipdown()
    {
        Intakeflip.setPosition(INTAKE_FLIP_DOWN_POSITION);
        flip = false;
    }

    public class IntakeAction implements Action
    {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            return true;
        }
    }
}
