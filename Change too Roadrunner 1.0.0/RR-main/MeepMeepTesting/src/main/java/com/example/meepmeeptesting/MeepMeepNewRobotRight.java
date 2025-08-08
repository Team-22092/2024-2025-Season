package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepNewRobotRight {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        // Create an action sequence
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-12, 62, Math.toRadians(270)))


                .strafeTo(new Vector2d(-12, 36)) //Score Spec 1
                .strafeTo(new Vector2d(-36, 36))
                .strafeTo(new Vector2d(-36, 12))
                .strafeTo(new Vector2d(-46, 12)) //Setup To Push Sample 1
                .strafeTo(new Vector2d(-46, 56)) //Push Sample 1
                .strafeTo(new Vector2d(-46, 12))
                .strafeTo(new Vector2d(-56, 12)) //Setup To Push Sample 2
                .strafeTo(new Vector2d(-56, 56)) //Push Sample 2
                .strafeTo(new Vector2d(-56, 12))
                .strafeTo(new Vector2d(-64, 12)) //Setup To Push Sample 3
                .strafeTo(new Vector2d(-64, 56)) //Push Sample 3
                .strafeTo(new Vector2d(-36, 50)) //Setup For Pickup Spec 2
                .strafeTo(new Vector2d(-36, 60)) //Pickup Spec 2
                .strafeTo(new Vector2d(-10, 36)) //Score Spec 2
                .strafeTo(new Vector2d(-36, 60)) //Pickup Spec 3
                .strafeTo(new Vector2d(-8, 36)) //Score Spec 3
                .strafeTo(new Vector2d(-36, 60)) //Pickup Spec 4
                .strafeTo(new Vector2d(-8, 36)) //Score Spec 4








                .build());

        // Set up MeepMeep visual properties
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
