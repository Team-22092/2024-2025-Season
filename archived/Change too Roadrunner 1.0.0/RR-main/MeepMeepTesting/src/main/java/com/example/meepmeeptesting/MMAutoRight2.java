package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MMAutoRight2 {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        // Create an action sequence
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12, 62, Math.toRadians(90)))

                        .lineToY(32) // Drops first spec
//                .afterTime(0, drive.SetLiftTarget(40))
                        .waitSeconds(0.2)
                        //first slide up






                        .lineToY(40)
                        .splineTo(new Vector2d(-34, 40), Math.toRadians(270))
                        .strafeTo(new Vector2d(-37, 11))


                        .splineToLinearHeading(new Pose2d(-60, 48, Math.toRadians(276)), Math.toRadians(270)) // Pushes sample to observation zone
                        .strafeTo(new Vector2d(-40, 50))
                        // .turn(Math.toRadians(6))
                        .strafeTo(new Vector2d(-40, 69)) // Picks second spec off wall

//                .afterTime(0, drive.SetLiftTarget(2049))
                        .waitSeconds(0.2)

                        .strafeTo(new Vector2d(-40, 60))
                        .strafeToLinearHeading(new Vector2d(-12, 48), Math.toRadians(90))

//                .strafeTo(new Vector2d(-40, 56))
//                .splineToLinearHeading(new Pose2d(-12, 48, 90), Math.toRadians(270))
//                .turn(Math.toRadians(-22.5))
                        .strafeTo(new Vector2d(-6, 30)) // Drops second spec on bar
//                .afterTime(0, drive.SetLiftTarget(40))

                        .waitSeconds(0.2)
                        //.splineToLinearHeading(new Pose2d(-40, 56, 90), Math.toRadians(270))


//                .strafeTo(new Vector2d(-40, 56))
//                .turn(Math.toRadians(180))

                        .strafeToLinearHeading(new Vector2d(-44, 56), Math.toRadians(272))
                        .strafeTo(new Vector2d(-44, 69)) // Picks third spec off wall

//                .afterTime(0, drive.SetLiftTarget(2049))
                        .waitSeconds(0.2)

                        .strafeTo(new Vector2d(-40, 60))
                        .strafeToLinearHeading(new Vector2d(-12, 48), Math.toRadians(90))

                        //  .turn(Math.toRadians(-22.5))
                        .strafeTo(new Vector2d(-2, 30)) // Drops third spec on bar
//                .afterTime(0, drive.SetLiftTarget(40))
                        .waitSeconds(0.2)

//                .strafeTo(new Vector2d(-40, 56)) // Returns to observation zone




                        .lineToY(34)

                        .strafeTo(new Vector2d(-35, 34))




                        .strafeTo(new Vector2d(-60, 11))


                        .strafeTo(new Vector2d(-60, 53))























                        .build()
        );

        // Set up MeepMeep visual properties
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
