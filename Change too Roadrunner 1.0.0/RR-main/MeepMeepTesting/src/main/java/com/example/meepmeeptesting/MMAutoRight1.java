package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MMAutoRight1 {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        // Create an action sequence
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12, 62, Math.toRadians(90)))
                        .lineToY(32)
                        .lineToY(40)
                        .splineTo(new Vector2d(-37, 40), Math.toRadians(270))
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
