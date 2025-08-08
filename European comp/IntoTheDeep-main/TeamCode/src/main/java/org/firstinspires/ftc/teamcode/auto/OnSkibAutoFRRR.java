package org.firstinspires.ftc.teamcode.auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Arclength;
import com.acmerobotics.roadrunner.CompositeAccelConstraint;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.PosePath;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Bucket;
import org.firstinspires.ftc.teamcode.hardware.Extendo;
import org.firstinspires.ftc.teamcode.hardware.Grabber;
import org.firstinspires.ftc.teamcode.hardware.Intake;
import org.firstinspires.ftc.teamcode.hardware.Lift;
import org.firstinspires.ftc.teamcode.hardware.TheThirdLeg;


@Autonomous(name = "OnSkibAutoFRRRR")
public class OnSkibAutoFRRR extends LinearOpMode {

    Grabber grabber;
    Lift lift;
    Intake intake;

    TheThirdLeg flicker;
    Bucket bucket;
    Extendo extendo;



    public class transfer implements Action
    { private double beginTs = -1;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            double t;
            if (beginTs < 0) {
                beginTs = com.acmerobotics.roadrunner.Actions.now();
                t = 0;
            } else {
                t = com.acmerobotics.roadrunner.Actions.now() - beginTs;
            }
            if(t >= 3.6)
            {
                intake.Left.setPower(1);
                intake.Right.setPower(1);
                lift.LIFTTARGET = 0;
                return false;
            }
            if(t >= 3.5)
            {
                lift.LIFTTARGET = 0;
                intake.Left.setPower(1);
                intake.Right.setPower(1);

              //  return false;


            }
            if(t >= 2.30) {
                intake.Flipup();
                intake.Left.setPower(0);
                intake.Right.setPower(0);
              lift.LIFTTARGET = 1000;

            }

             else if (t >=1.70) {
              //  extendo.OUTTARGET = 100;
                intake.Left.setPower(-1);
                intake.Right.setPower(1);

            } else if (t >= 1.30){
                intake.Left.setPower(0);
                intake.Right.setPower(0);
                extendo.OUTTARGET = 100;

            } else if (t >= 1){

                 intake.Flipdown();//flipup
            } else {
                intake.Left.setPower(1);
                intake.Right.setPower(-1);
                extendo.OUTTARGET = 800;

            }
            return true;
        }
    }







    public class Lastone implements Action
    { private double beginTs = -1;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            double t;
            if (beginTs < 0) {
                beginTs = com.acmerobotics.roadrunner.Actions.now();
                t = 0;
            } else {
                t = com.acmerobotics.roadrunner.Actions.now() - beginTs;
            }
            if(t >= 2.9)
            {

                lift.LIFTTARGET = 0;
                return false;
            }
            else if(t >= 2.30) {
                intake.Flipup();
                intake.Left.setPower(0);
                intake.Right.setPower(0);
                lift.LIFTTARGET = 1000;

            }

            else if (t >=1.70) {

                intake.Left.setPower(-1);
                intake.Right.setPower(1);

            } else if (t >= 1.30){
                intake.Left.setPower(0);
                intake.Right.setPower(0);
                extendo.setspeed(0.85f);
                extendo.OUTTARGET = 100;

            } else if (t >= 1){

                intake.Left.setPower(1);
                intake.Right.setPower(-1);
                intake.Flipdown();//flipup
            } else if( t >= 0.1)
            {
                intake.Left.setPower(1);
                intake.Right.setPower(-1);
                extendo.setspeed(0.85f);
                extendo.OUTTARGET = 1300;

            }


            else {
                intake.Left.setPower(1);
                intake.Right.setPower(-1);
            }
            return true;
        }
    }





    @Override
    public void runOpMode() {
        bucket = new Bucket(hardwareMap);
        grabber = new Grabber(hardwareMap);
        intake = new Intake(hardwareMap);
        lift = new Lift(hardwareMap, intake);
        flicker = new TheThirdLeg(hardwareMap);
        extendo = new Extendo(hardwareMap, true);
        // TODO - MAKE SURE TO UPDATE INITIAL POSITION
        Pose2d initialPose =  new Pose2d(10, -60, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Action MainAction = drive.actionBuilder(initialPose)

                // TODO - PUT THE MEEPMEEP PATH IN HERE
                .afterTime(0, new InstantAction(() -> bucket.BucketHoldPos()))
                //go to submersible
                .afterTime(0,new InstantAction(() -> intake.Flipmid()))


                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 1500))

                .afterDisp(24, new InstantAction(() -> lift.LIFTTARGET = 0))

                .strafeToLinearHeading(new Vector2d(3,-31),Math.toRadians(270))

           //     .afterTime(0, new InstantAction(() -> flicker.Hitout(1)))




                //backaway, and strafe
               // .strafeToLinearHeading(new Vector2d(30,-40),Math.toRadians(-270))


                .strafeToLinearHeading(new Vector2d(50.5,-38),Math.toRadians(-268))




                .afterTime(0, new ParallelAction(
                        new InstantAction(() -> intake.Flipup()),
                        new InstantAction(() -> intake.Left.setPower(1)),
                        new InstantAction(() -> intake.Right.setPower(-1)),
                        new transfer()
                ))


          /*      .strafeToLinearHeading(new Vector2d(50.5,-47),Math.toRadians(-270))
                */
                .strafeToLinearHeading(new Vector2d(48,-40),Math.toRadians(-270), new TranslationalVelConstraint(20), new ProfileAccelConstraint(-20, 20))

               // .strafeToLinearHeading(new Vector2d(48,-36),Math.toRadians(-270), new TranslationalVelConstraint(20))

                .afterTime(0, new ParallelAction(
                        new InstantAction(() -> lift.LIFTTARGET = 0),
                        new InstantAction(() -> bucket.BucketHoldPos()),
                        new InstantAction(() -> intake.Flipdown())
                ))

                .waitSeconds(1.5)

           //     .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 3000))
//


                .afterTime(0, new InstantAction(() -> bucket.dumpBigger()))

             //   .strafeToLinearHeading(new Vector2d(57,-47),Math.toRadians(-270))

                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 0))


                .strafeToLinearHeading(new Vector2d(60.5,-38),Math.toRadians(-270))

                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 0))


                .afterTime(0, new ParallelAction(
                        new InstantAction(() -> bucket.BucketHoldPos()),
                        new InstantAction(() -> intake.Flipup()),
                        new InstantAction(() -> lift.LIFTTARGET = 0),
                        new InstantAction(() -> intake.Left.setPower(1)),
                        new InstantAction(() -> intake.Right.setPower(-1)),
                        new InstantAction(() -> bucket.BucketHoldPos()),
                        new InstantAction(() -> lift.LIFTTARGET = 0),
                        new InstantAction(() ->  lift.LIFTTARGET = 0),

                        new transfer()
                ))



                .waitSeconds(2)

                .afterTime(0, new InstantAction(() -> bucket.dumpBigger()))

//
//
                .waitSeconds(0.5)


                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 0))

           //     .turn( Math.toRadians(-30))
              //  .strafeToLinearHeading(new Vector2d(68,-43),)




                .strafeToLinearHeading(new Vector2d(63.5,-38),Math.toRadians(-299.5))//305 i think was the old one

                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 0))


                .afterTime(0, new ParallelAction(
                        new InstantAction(() -> bucket.BucketHoldPos()),
                        new InstantAction(() -> intake.Flipup()),
                        new InstantAction(() -> lift.LIFTTARGET = 0),
                        new InstantAction(() -> intake.Left.setPower(1)),
                        new InstantAction(() -> intake.Right.setPower(-1)),
                        new InstantAction(() -> bucket.BucketHoldPos()),
                        new InstantAction(() -> lift.LIFTTARGET = 0),
                        new InstantAction(() ->  lift.LIFTTARGET = 0),

                        new Lastone()
                ))


                .waitSeconds(2)

                .afterTime(0, new InstantAction(() -> bucket.dumpBigger()))

//
//
                .waitSeconds(0.5)


                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 0))




//                .afterTime(0, new InstantAction(() -> intake.Left.setPower(1)))
//                .afterTime(0, new InstantAction(() ->   intake.Right.setPower(-1)))

//

                .afterTime(0, new ParallelAction(
                        new InstantAction(() -> lift.LIFTTARGET = 0)

                ))

                .afterTime(0, grabber.new open())

                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 0))
//

//
//
//                .strafeTo(new Vector2d(55,-30))
                .turn(Math.toRadians(30))
               // .strafeToLinearHeading(new Vector2d(40,-60),Math.toRadians(-270), new TranslationalVelConstraint(20), new ProfileAccelConstraint(-30, 30))
//
//
                .strafeToLinearHeading(new Vector2d(40,-62),Math.toRadians(-270))


          //      .strafeTo(new Vector2d(40,-60))



                .afterTime(0, new ParallelAction(
                        new InstantAction(() -> lift.LIFTTARGET = 0)

                ))

                .afterTime(0, grabber.new open())

                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 0))
//

//                .turn(Math.toRadians(-23))











//
//
//
//
//
//
////                .afterTime(0,new InstantAction(() ->   intake.Left.setPower(0)))
////                .afterTime(0,new InstantAction(() ->      intake.Right.setPower(0)
////                ))
////
////                //intake, then flip
////                .afterTime(0,new InstantAction(() ->   intake.Left.setPower(1)))
////                .afterTime(0,new InstantAction(() ->      intake.Right.setPower(-1)
////                ))
//
////                .afterTime(0, new transfer())
//
//
                .afterTime(0, new ParallelAction(
                        new InstantAction(() -> lift.LIFTTARGET = 0)

                ))

                .afterTime(0, grabber.new open())

                .afterTime(0, new InstantAction(() -> lift.LIFTTARGET = 0))
//
//
//
//
//
//
//
////                //pickup first one
////
//                .strafeToLinearHeading(new Vector2d(41, -60), Math.toRadians(-270))
////                .strafeTo(new Vector2d(38, -60))
//
                .waitSeconds(0.1)
                .afterTime(0, grabber.new close())

                .afterTime(0.1, new InstantAction(() -> lift.LIFTTARGET = 1500))
                .waitSeconds(0.1)
//
                // score!!!! #2
               // .waitSeconds(0.5)


              //  .strafeToLinearHeading(new Vector2d(0,-29),Math.toRadians(270), new TranslationalVelConstraint(30), new ProfileAccelConstraint(-40, 60))


                .strafeToLinearHeading(new Vector2d(0,-34),Math.toRadians(270))
                .afterDisp(4, new InstantAction(() -> lift.LIFTTARGET = 0))
     .strafeToLinearHeading(new Vector2d(0,-30),Math.toRadians(270), new TranslationalVelConstraint(30), new ProfileAccelConstraint(-40, 60) )
              //  .strafeTo(new Vector2d(0,-27))


//
//////               // .waitSeconds(1)
////
////                //pickup score #3
                .strafeToLinearHeading(new Vector2d(43,-60), Math.toRadians(-270))
                .afterTime(0, grabber.new open())
                .strafeToLinearHeading(new Vector2d(43,-65), Math.toRadians(-270))
//                .strafeTo(new Vector2d(38, -60))
                .waitSeconds(0.1)


                .afterTime(0, grabber.new close())

                .afterTime(0.1, new InstantAction(() -> lift.LIFTTARGET = 1500))
                .waitSeconds(0.1)
//

               // .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(-4,-34),Math.toRadians(270) )
                .afterDisp(3, new InstantAction(() -> lift.LIFTTARGET = 0))
                .strafeToLinearHeading(new Vector2d(-4,-30),Math.toRadians(270), new TranslationalVelConstraint(30), new ProfileAccelConstraint(-40, 60))
              //  .strafeTo(new Vector2d(-4,-27))
               // .waitSeconds(1)
////
////
////                //pickup score #4
//                .afterTime(0, grabber.new open())
                .strafeToLinearHeading(new Vector2d(43,-60), Math.toRadians(-270))
                .afterTime(0, grabber.new open())
                .strafeToLinearHeading(new Vector2d(43,-65), Math.toRadians(-270))

//                .strafeTo(new Vector2d(38, -60))
                .waitSeconds(0.1)

                .afterTime(0, grabber.new close())
                .afterTime(0.1, new InstantAction(() -> lift.LIFTTARGET = 1500))
                .waitSeconds(0.1)

                //.afterDisp(56, new InstantAction(() -> lift.LIFTTARGET = 0))
                // .waitSeconds(0.5)
               // .strafeToLinearHeading(new Vector2d(-6,-29),Math.toRadians(270), new TranslationalVelConstraint(30), new ProfileAccelConstraint(-40, 60))



                .strafeToLinearHeading(new Vector2d(-6,-34),Math.toRadians(270))
                .afterDisp(10, new InstantAction(() -> lift.LIFTTARGET = 0))
                .strafeToLinearHeading(new Vector2d(-6,-28),Math.toRadians(270))

               // .afterTime(0, grabber.new close())
               // .strafeTo(new Vector2d(-6,-27))
//
//                //parks-
          //      .strafeToLinearHeading(new Vector2d(35, -55), Math.toRadians(270))
//////
//
//
//
//
//
//
//
//





                // TODO - END OF MEEPMEEP PATH

                .build();

        telemetry.addLine("READY! GOOD LUCK :)");
        telemetry.update();
        grabber.Startclose();



        waitForStart();

        Actions.runBlocking(new ParallelAction(

                MainAction,
                extendo.new OutLoopAction(),
                lift.new LiftLoopActionSpec()
        ));

    }




}
