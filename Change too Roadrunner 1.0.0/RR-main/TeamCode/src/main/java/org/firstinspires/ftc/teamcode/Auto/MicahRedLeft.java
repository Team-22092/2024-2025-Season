package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="MicahMeepMeepBlLeft")
public class MicahRedLeft extends LinearOpMode {
    @Override
public void runOpMode() {
    // instantiate your MecanumDrive at a particular pose.
    Pose2d initialPose = new Pose2d(12, 62, Math.toRadians(90));
    MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)

                .lineToY(-34) // drops off specimen
                .lineToY(-48)
                .turn(Math.toRadians(-135))
                .strafeTo(new Vector2d(-39, -36)) // picks up first sample
                .waitSeconds(3)
                .strafeTo(new Vector2d(-56, -52)) // scores first basket
                .waitSeconds(3)
                .strafeTo(new Vector2d(-46, -36)) // picks up second sample
                .waitSeconds(3)
                .strafeTo(new Vector2d(-56, -52)) // scores second basket
                .waitSeconds(3)
                .turn(Math.toRadians(-35))
                .splineTo(new Vector2d(-26, -0), Math.toRadians(0)); // goes to end zone for auto



        Action a = tab1.build();
        waitForStart();
        if (isStopRequested()) return;
        Actions.runBlocking(a);

}
}
