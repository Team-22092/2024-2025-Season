package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepPickUpBlR {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        // Create an action sequence
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12, 62, Math.toRadians(90)))

                .lineToY(32) // drops first specimen
                .lineToY(33)
                .splineTo(new Vector2d(-37, 33), Math.toRadians(270))
                .strafeTo(new Vector2d(-37, 11))
                .strafeTo(new Vector2d(-45, 11))
                .strafeTo(new Vector2d(-45, 54)) // pushes for first sample
                .strafeTo(new Vector2d(-45, 11))
                .strafeTo(new Vector2d(-56, 11))
                .strafeTo(new Vector2d(-56, 56)) // pushes for second sample
                .strafeTo(new Vector2d(-56, 56))
                .strafeTo(new Vector2d(-40, 56))
                .strafeTo(new Vector2d(-40, 70)) // picks up second specimen
                .strafeTo(new Vector2d(-40, 56))
                .strafeTo(new Vector2d(-40, 48))
                .turn(Math.toRadians(90))
                .strafeTo(new Vector2d(-10, 48))
                .turn(Math.toRadians(90))
                .strafeTo(new Vector2d(-10, 32)) // scores second specimen
                .strafeTo(new Vector2d(-10, 48))
                .turn(Math.toRadians(180))
                .strafeTo(new Vector2d(-40, 56)) // picks up third specimen/goes home
//                .strafeTo(new Vector2d(-40, 58))
//                .turn(Math.toRadians(90))
//                .strafeTo(new Vector2d(-6, 48))
//                .turn(Math.toRadians(90))
//                .strafeTo(new Vector2d(-6, 32))














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
