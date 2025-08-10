package org.firstinspires.ftc.teamcode.Auto;

import android.os.Build;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="MicahMeepMeep: Auto Drive By Encoder")
public class MicahMeep extends LinearOpMode {
    @Override
public void runOpMode() {
    // instantiate your MecanumDrive at a particular pose.
    Pose2d initialPose = new Pose2d(0, 62, Math.toRadians(90));
    MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .lineToY(40)
                .turn(Math.toRadians(90));



        Action a = tab1.build();
        waitForStart();
        if (isStopRequested()) return;
        Actions.runBlocking(a);

}
}
