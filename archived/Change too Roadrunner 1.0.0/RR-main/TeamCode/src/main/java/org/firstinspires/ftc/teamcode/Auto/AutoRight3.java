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
@Autonomous(name="Global 3 right")
public class AutoRight3 extends LinearOpMode {
    @Override
    public void runOpMode() {
        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(-12, 62, Math.toRadians(90));
        Servo SigmaServo;


        SigmaServo  = hardwareMap.get(Servo.class, "trayServo");

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
//        MecanumDrive.LiftAction ArmUp = drive.new LiftAction(-2049);
//
//        MecanumDrive.LiftAction ArmMid = drive.new LiftAction(-1050);
//
//
//        MecanumDrive.LiftAction ArmDown = drive.new LiftAction(-40);
        VelConstraint slowmode = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(20),
                new AngularVelConstraint(Math.PI / 2)
        ));

        VelConstraint Superslowmode = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(10),
                new AngularVelConstraint(Math.PI / 2)
        ));


        VelConstraint SuperFast = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(110),
                new AngularVelConstraint(Math.PI)
        ));


//***********************************************************************************************************

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .lineToY(34, SuperFast); // drops first specimen
        Action GotoBar = tab1.build();

//***********************************************************************************************************

        Action Pressbar = tab1.endTrajectory().fresh()
                .lineToY(32, slowmode)
                .build();// drops first specimen

//***********************************************************************************************************

        TrajectoryActionBuilder AfterDropOff1 = tab1.endTrajectory().fresh()


                .lineToY(40)
                .splineTo(new Vector2d(-37, 40), Math.toRadians(270))
                .strafeTo(new Vector2d(-37, 11))
                //.strafeTo(new Vector2d(-45, 11))


                .strafeTo(new Vector2d(-70, 54)) // pushes for first sample




                //.strafeTo(new Vector2d(-56, 56))

                .strafeTo(new Vector2d(-40, 50))
                .turn(Math.toRadians(6))
                .strafeTo(new Vector2d(-40, 69), slowmode); //pick up from wall








//                .strafeTo(new Vector2d(-10, 48))
//                .turn(Math.toRadians(180))
//                .strafeTo(new Vector2d(-40, 56))
//                .strafeTo(new Vector2d(-12, 48))
//                .turn(Math.toRadians(187.5))
//                .strafeTo(new Vector2d(-10, 34)) // drops second specimen
//                .strafeTo(new Vector2d(-45, 58))
        Action AfterDropOff1Action = AfterDropOff1.build();

//***********************************************************************************************************

        TrajectoryActionBuilder AfterPreraiseArm = AfterDropOff1.endTrajectory().fresh()

                .strafeTo(new Vector2d(-40, 56), SuperFast)
                .strafeTo(new Vector2d(-40, 48), SuperFast)
                .turn(Math.toRadians(180))
                .strafeTo(new Vector2d(-6, 48), SuperFast)

                .strafeTo(new Vector2d(-6, 32));





        Action AfterPreraiseArmAction = AfterPreraiseArm.build();


//***********************************************************************************************************
        Action AfterDropOff2 = AfterPreraiseArm.endTrajectory().fresh()

//                .strafeTo(new Vector2d(-6, 48))
//                .turn(Math.toRadians(180))
                .strafeTo(new Vector2d(-40, 56))


                .build();

//***********************************************************************************************************

        TrajectoryActionBuilder AfterDrop2 = AfterPreraiseArm.endTrajectory().fresh()

//                .strafeTo(new Vector2d(-6, 48))
//                .turn(Math.toRadians(180))
                .strafeTo(new Vector2d(-40, 56));




        Action AfterDrop2Action = AfterDrop2.build();


        TrajectoryActionBuilder turn = AfterDrop2.endTrajectory().fresh()

//                .strafeTo(new Vector2d(-6, 48))
//                .turn(Math.toRadians(180))

                .strafeTo(new Vector2d(-40, 56));



        Action turnAction = turn.build();



        TrajectoryActionBuilder test = AfterDrop2.endTrajectory().fresh()

//                .strafeTo(new Vector2d(-6, 48))
//                .turn(Math.toRadians(180))

                .turn(Math.PI);



        Action testAction = test.build();



        Action grab3 = turn.endTrajectory().fresh()

                .strafeTo(new Vector2d(-40, 50))
                .turn(Math.toRadians(6))
                .strafeTo(new Vector2d(-40, 69), slowmode)



                .build();


        SigmaServo.setPosition(0f);



//        Action p = new ParallelAction(
//                ArmUp, GotoBar);
//
//        Action p2 =  new ParallelAction(
//                AfterDropOff1Action, ArmDown);
//
//        Action p3  =  new ParallelAction(
//                AfterDropOff2, ArmDown);



//        Action action =  new SequentialAction(
//
//
//                p,
//
//
//                Pressbar, ArmMid,
//
//
//                p2,
//
//                AfterDropOff1Action, ArmUp,
//                AfterPreraiseArmAction,
//                GotoBar,
//
//                p3,
//
//                Pressbar, ArmMid,
//
//
//                AfterDrop2Action, testAction
//
////                turnAction, grab3, GotoBar,
////
////                p3,
////
////                Pressbar, ArmMid
//
//
//





//        );
//
//
//
//        waitForStart();
//        if (isStopRequested()) return;
//        Actions.runBlocking(
//                action
//        );

    }
}
