package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class FivePointsMM {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        // Create an action sequence
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12, 62, Math.toRadians(-90)))

                        .lineToY(32)
                        .strafeToLinearHeading(new Vector2d(-36, 38), Math.toRadians(250))
                        //extend arm


                        .turn(Math.toRadians(-100))
                        .turn(Math.toRadians(80))

                        //extend arm

                        .turn(Math.toRadians(-80))
                        .turn(Math.toRadians(60))

                        //extend arm

                        .turn(Math.toRadians(-60))




                //pickup & place
                .strafeToLinearHeading(new Vector2d(-40, 60), Math.toRadians(-90))
                .strafeTo(new Vector2d(-8, 32))




                //pickup & place
                .strafeTo(new Vector2d(-40, 60))
                .strafeTo(new Vector2d(-8, 32))


                //pickup & place
                .strafeTo(new Vector2d(-40, 60))
                .strafeTo(new Vector2d(-8, 32))


                //pickup & place
                .strafeTo(new Vector2d(-40, 60))
                .strafeTo(new Vector2d(-8, 32))

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
