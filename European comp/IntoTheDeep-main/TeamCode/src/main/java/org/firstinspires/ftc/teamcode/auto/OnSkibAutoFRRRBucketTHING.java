package org.firstinspires.ftc.teamcode.auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
//import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Bucket;
import org.firstinspires.ftc.teamcode.hardware.Extendo;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Lift;

import java.lang.Math;

@Autonomous(name = "OnSkibAutoFRRRBucketTHING \uD83D\uDC45 \uD83D\uDC45")
public class OnSkibAutoFRRRBucketTHING extends LinearOpMode {

    Bucket bucket;
    Extendo extendo;
    Lift lift;

    Intake intake;


    public class transfer implements Action
    { private double beginTs = -1;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            double t;
            if (beginTs < 0) {
                beginTs = Actions.now();
                t = 0;
            } else {
                t = Actions.now() - beginTs;
            }

            if (t >= 4.25) {
                intake.Left.setPower(0);
                intake.Right.setPower(0);
                intake.Flipup(); //flipup
                extendo.OUTTARGET = 440;
                return false;
            } else if (t >=3.75) {
                intake.Left.setPower(-1);
                intake.Right.setPower(1);
            } else if (t >= 2.75){
                extendo.OUTTARGET = 232;
                intake.Flipdown(); //flipdown
            } else if (t >= 1.75){
                intake.Left.setPower(0);
                intake.Right.setPower(0);
            } else {
                intake.Left.setPower(1);
                intake.Right.setPower(-1);
                extendo.OUTTARGET = 480;

            }
            return true;
        }
    }



    @Override
    public void runOpMode() {
        bucket = new Bucket(hardwareMap);
        extendo = new Extendo(hardwareMap, true);
        intake = new Intake(hardwareMap);
        lift = new Lift(hardwareMap, intake);
        // TODO - MAKE SURE TO UPDATE INITIAL POSITION
        Pose2d initialPose =  new Pose2d(40, 63, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Action MainAction = drive.actionBuilder(initialPose)
                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 3200))
                .waitSeconds(1.4)


                .strafeToLinearHeading(new Vector2d(60, 61), Math.toRadians(-140))
                .afterDisp(3, new InstantAction(() -> bucket.dump()))
                .afterTime(0, new InstantAction(() -> extendo.OUTTARGET = 238))
                .afterTime(0, new InstantAction(() -> intake.Flipup())) //flipup
                .waitSeconds(0.7)



                .afterTime(0, new transfer())
                .strafeToLinearHeading(new Vector2d(51, 40), Math.toRadians(-90))
                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 0))
                .afterTime(0, new InstantAction(() -> bucket.BucketHoldPos()))
                .waitSeconds(3.5)

                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 3200))
                .waitSeconds(1.1)
                .strafeToLinearHeading(new Vector2d(63, 61), Math.toRadians(-140))
                .afterDisp(3, new InstantAction(() -> bucket.dump()))
                .waitSeconds(0.7)

                .afterTime(0, new transfer())
                .strafeToLinearHeading(new Vector2d(62, 40), Math.toRadians(-90))
                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 0))
                .afterTime(0, new InstantAction(() -> bucket.BucketHoldPos()))
                .waitSeconds(3.5)



                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 3200))
                .waitSeconds(1.2)
                .strafeToLinearHeading(new Vector2d(63, 61), Math.toRadians(-140))
                .afterDisp(3, new InstantAction(() -> bucket.dump()))
                .waitSeconds(0.7)

//


                .strafeToLinearHeading(new Vector2d(56,44), Math.toRadians(310))
                .afterTime(0, new transfer())
                .strafeTo(new Vector2d(66, 34))
                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 0))
                .afterTime(0, new InstantAction(() -> bucket.BucketHoldPos()))
                .waitSeconds(3.5)



                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 3200))
                .waitSeconds(1)
                .afterTime(0,new InstantAction(() -> intake.Flipmid()))
                .afterTime(0,new InstantAction(() -> extendo.OUTTARGET = 0))
                .strafeToLinearHeading(new Vector2d(63,61), Math.toRadians(-140))
                .afterDisp(3, new InstantAction(() -> bucket.dump()))
                .waitSeconds(0.7)
                .strafeToLinearHeading(new Vector2d(58,56), Math.toRadians(-140))


                //park
//                .strafeTo(new Vector2d(60, 50))
//                .strafeToLinearHeading(new Vector2d(35, 10), Math.toRadians(0))




                // TODO - END OF MEEPMEEP PATH

                .build();

        telemetry.addLine("READY! GOOD LUCK :)");
        telemetry.update();
        bucket.BucketHoldPos();


        waitForStart();
        intake.Flipmid();
        extendo.OUTTARGET = 238;

        com.acmerobotics.roadrunner.ftc.Actions.runBlocking(new ParallelAction(

                MainAction,
                lift.new LiftLoopActionSample(),
                extendo.new OutLoopAction()
        ));

    }




}
