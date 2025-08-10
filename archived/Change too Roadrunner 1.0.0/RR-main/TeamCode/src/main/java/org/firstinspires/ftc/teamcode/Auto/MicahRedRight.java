package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="MicahMeepMeepBlRight")
public class MicahRedRight extends LinearOpMode {
    @Override
public void runOpMode() {
    // instantiate your MecanumDrive at a particular pose.
    Pose2d initialPose = new Pose2d(-12, 62, Math.toRadians(90));
    MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .lineToY(36) // drops first specimen
                .lineToY(48)
                .splineTo(new Vector2d(40, -48), Math.toRadians(90))
                .strafeTo(new Vector2d(40, -11))
                .strafeTo(new Vector2d(45, -11))
                .strafeTo(new Vector2d(45, -58)) // goes for first sample
                .strafeTo(new Vector2d(45, -11))
                .strafeTo(new Vector2d(56, -11))
                .strafeTo(new Vector2d(56, -58)) // goes for second sample
                .strafeTo(new Vector2d(56, -11))
                .strafeTo(new Vector2d(64, -11))
                .strafeTo(new Vector2d(64, -58)) // goes for third sample
                .strafeTo(new Vector2d(12, -48))
                .turn(Math.toRadians(172.5))
                .strafeTo(new Vector2d(10, -34)) // drops second specimen
                .strafeTo(new Vector2d(45, -58));



        Action a = tab1.build();
        waitForStart();
        if (isStopRequested()) return;
        Actions.runBlocking(a);

}
}
