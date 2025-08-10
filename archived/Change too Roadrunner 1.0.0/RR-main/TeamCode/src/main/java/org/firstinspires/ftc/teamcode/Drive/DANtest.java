package org.firstinspires.ftc.teamcode.Drive;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//package org.openftc.i2cdrivers;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.PIDController;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;


import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

public class DANtest {
    private CRServo L;
    private CRServo R;


    public void init() {
        L = hardwareMap.get(CRServo.class, "L");
        R = hardwareMap.get(CRServo.class, "R");
    }


    public void loop() {

        if(gamepad2.right_bumper)
        {

            R.setPower(1);
        }
        else if (gamepad2.left_bumper)
        {
            L.setPower(1);
        }

    }
}

