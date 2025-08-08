package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepPickUpReL {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12, -62, Math.toRadians(270)))


                .lineToY(-34) // drops off specimen
                .lineToY(-48)
                .turn(Math.toRadians(-135))
                .strafeTo(new Vector2d(-39, -36)) // picks up first sample
                .waitSeconds(3)
                .strafeTo(new Vector2d(-56, -52)) // scores first basket
                .waitSeconds(3)
                .strafeTo(new Vector2d(-46, -36)) // picks up second sample
                .waitSeconds(3)
                .strafeTo(new Vector2d(-56, -52)) // scores second basket
                .waitSeconds(3)
                .turn(Math.toRadians(-35))
                .splineTo(new Vector2d(-26, -0), Math.toRadians(0)) // goes to end zone for auto





















                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}