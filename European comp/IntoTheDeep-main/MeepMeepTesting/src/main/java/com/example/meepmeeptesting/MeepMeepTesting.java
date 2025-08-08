package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(6, -27, Math.toRadians(270)))
                //first specimen on bot scored

                .strafeToLinearHeading(new Vector2d(3,-31),Math.toRadians(270))







                //backaway, and strafe
                .strafeToLinearHeading(new Vector2d(30,-40),Math.toRadians(-270))


                .strafeToLinearHeading(new Vector2d(47,-47),Math.toRadians(-270))



                .strafeToLinearHeading(new Vector2d(47,-37),Math.toRadians(-270)) //intake, then flip



                .strafeToLinearHeading(new Vector2d(57,-47),Math.toRadians(-270))



                .strafeToLinearHeading(new Vector2d(57,-37),Math.toRadians(-270))







//                                .str














//                //go to spike mark #1
//                .splineToConstantHeading(new Vector2d(44,-16),Math.toRadians(-40))
//
//
//                //push sample to zone
//                .strafeToLinearHeading(new Vector2d(48,-40),Math.toRadians(-270))
////                .afterTime(0, grabber.new open())
//
//                //go to spike mark #2
//                .splineToConstantHeading(new Vector2d(50,-12),Math.toRadians(-5))
//
//
//                //push sample to zone
//                .strafeToLinearHeading(new Vector2d(60,-45),Math.toRadians(-270))

             //   .strafeTo(new Vector2d(60, -43))
                //pickup first one

                .strafeToLinearHeading(new Vector2d(40, -60), Math.toRadians(-270))
//                .strafeTo(new Vector2d(38, -60))
                .waitSeconds(0.2)
//                .afterTime(0, grabber.new close())
//
//                .afterTime(0.1, new InstantAction(() -> lift.LIFTTARGET = 2000))
                .waitSeconds(0.3)
//
//                // score!!!! #2
//               // .waitSeconds(0.5)
//
//                .afterDisp(48, new InstantAction(() -> lift.LIFTTARGET = 0))
                .strafeToLinearHeading(new Vector2d(0,-26),Math.toRadians(270))
//                .afterTime(0, grabber.new open())

//               // .waitSeconds(1)

//                //pickup score #3
                .strafeToLinearHeading(new Vector2d(41,-62),Math.toRadians(-270))
//                .strafeTo(new Vector2d(38, -60))
                .waitSeconds(0.2)
//                .afterTime(0, grabber.new close())
//
//                .afterTime(0.1, new InstantAction(() -> lift.LIFTTARGET = 2000))
                .waitSeconds(0.2)

//                .afterDisp(53, new InstantAction(() -> lift.LIFTTARGET = 0))
                // .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(-4,-26),Math.toRadians(270))

                // .waitSeconds(1)
//
//
//                //pickup score #4
//                .afterTime(0, grabber.new open())
                .strafeToLinearHeading(new Vector2d(41,-62),Math.toRadians(-270))

//                .strafeTo(new Vector2d(38, -60))
                .waitSeconds(0.2)
//                .afterTime(0, grabber.new close())
//
//                .afterTime(0.1, new InstantAction(() -> lift.LIFTTARGET = 2000))
                .waitSeconds(0.2)

                //.afterDisp(54, new InstantAction(() -> lift.LIFTTARGET = 0))
                // .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(-6,-27),Math.toRadians(270))
//
//                //parks-
                .strafeToLinearHeading(new Vector2d(35, -55), Math.toRadians(270))
//
//
//
//
//
//
//
//












                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}