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
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Drive.AutoActions;
import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.Arrays;

@Autonomous(name="Global 4 left - sigmamode")
public class KallensSkibity3 extends LinearOpMode {
    @Override
public void runOpMode() {
        AutoActions AA;
    // instantiate your MecanumDrive at a particular pose.
    Pose2d initialPose = new Pose2d(-50, -62, Math.toRadians(90));

    AA = new AutoActions(hardwareMap, true);
    MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        TrajectoryActionBuilder Main = drive.actionBuilder(initialPose)

                .strafeTo(new Vector2d(-65, -50))
                .afterTime(0, AA.SetSlideOutTargetPos(600))
                .turn(Math.toRadians(-45))

                .lineToY(-60)

               // .afterTime(0, AA.SetClawTargetAction(0.3))

                .afterTime(0, AA.SCORE2CHILL())
                .afterTime(1, AA.Samplechillhelper())
                .afterTime(5, AA.SetSlideOutTargetPos(40));





//                .strafeTo(new Vector2d(-66, -40))
//
//                .turn(Math.toRadians(45))
//                .lineToY(-30)
//                .afterTime(0, AA.AutoTransfer())
//                .lineToY(-20)
//                .lineToY(-55)
//                .afterTime(3, AA.IntakeAuto());


//


        //SUUUUUUUUUCKKKK pause


//                .strafeTo(new Vector2d(-60, -50))
//                .turn(Math.toRadians(-45))
//                .lineToY(-60);






                //.strafeToLinearHeading(new Vector2d(60, 40), Math.toRadians(290));
                //score point

//
             //   .strafeToLinearHeading(new Vector2d(50, 40), Math.toRadians(290));
//
//
//
//
//                //score point again
//                .strafeToLinearHeading(new Vector2d(60, 50), Math.toRadians(270))
//
//
//
//                //grab point
//                .strafeTo(new Vector2d(60, 40))
//
//                //score again
//
//
//
//
//                .strafeTo(new Vector2d(60, 50))
//
//
//
//                //grab point
//                .strafeTo(new Vector2d(50, 40))
//
//
//                //score
//                .strafeTo(new Vector2d(60, 50));
//
//
//
//
//
//



        Action MainAction = new ParallelAction(
                AA.LoopAuto(),
                Main.build()

        );



//***********************************************************************************************************




        AA.Claw.setPosition(0.6);
        AA.Flip.setPosition(0.1);

        waitForStart();
        AA.ClawTargetPos = 0.6;
        AA.FlipTargetPos = 0.0;
        AA.SlideOutTargetPos = 600;
//        AA.Intakeflip.setPosition(0.25);
        AA.LiftTargetPos = 4050;

        if (isStopRequested()) return;
        Actions.runBlocking(
              MainAction
        );

}
}
