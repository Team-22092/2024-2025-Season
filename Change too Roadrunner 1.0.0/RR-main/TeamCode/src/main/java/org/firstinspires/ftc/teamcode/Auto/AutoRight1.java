package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.Arrays;

@Disabled
@Autonomous(name="AutoRight1")
public class AutoRight1 extends LinearOpMode {
    @Override
    public void runOpMode() {
        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(-12, 62, Math.toRadians(90));
        Servo SigmaServo;


        SigmaServo  = hardwareMap.get(Servo.class, "trayServo");

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
//        MecanumDrive.LiftAction LA = drive.new LiftAction(-2049);
//        MecanumDrive.LiftAction LB = drive.new LiftAction(-40);
        VelConstraint baseVelConstraint = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(20),
                new AngularVelConstraint(Math.PI / 2)
        ));
        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .lineToY(34, baseVelConstraint); // drops first specimen



        Action a = tab1.build();


        Action C = tab1.endTrajectory().fresh()
                .lineToY(32, baseVelConstraint)
                .build();// drops first specimen



        Action B = tab1.endTrajectory().fresh()

                .lineToY(48)
                .splineTo(new Vector2d(-37, 48), Math.toRadians(270))
                .strafeTo(new Vector2d(-37, 11))
                .strafeTo(new Vector2d(-45, 11))
                .strafeTo(new Vector2d(-45, 58)) // goes for first sample
                .strafeTo(new Vector2d(-45, 11))
                .strafeTo(new Vector2d(-56, 11))
                .strafeTo(new Vector2d(-56, 58)) // goes for second sample
                .strafeTo(new Vector2d(-56, 11))
                .strafeTo(new Vector2d(-64, 11))
                .strafeTo(new Vector2d(-64, 58)) // goes for third sample
                .lineToY(48)
                .waitSeconds(1.5)
                .lineToY(58)
//                .strafeTo(new Vector2d(-12, 48))
//                .turn(Math.toRadians(187.5))
//                .strafeTo(new Vector2d(-10, 34)) // drops second specimen
//                .strafeTo(new Vector2d(-45, 58))
                .build();




        SigmaServo.setPosition(0f);
        waitForStart();
        if (isStopRequested()) return;
        Actions.runBlocking(

                new SequentialAction(

                        new ParallelAction(
//                                LA, a

                        ),




//                        C, LB,



                        B
                )
        );

    }
}
