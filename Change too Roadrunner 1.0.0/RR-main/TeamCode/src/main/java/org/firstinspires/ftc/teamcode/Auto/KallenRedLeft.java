package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Auto.EasyOpenCV.AutoLineUP;
import org.firstinspires.ftc.teamcode.Auto.EasyOpenCV.OpenCVBlue;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.Arrays;
import java.util.Objects;

@Autonomous(name="COLOR TEST --DO NOT RUN IN GAME--")
public class KallenRedLeft extends LinearOpMode {
    @Override

    public void runOpMode() {

        OpenCvWebcam webcam;
        OpenCVBlue pipeline = new OpenCVBlue(telemetry);



        DcMotor FLMotor, FRMotor, BRMotor, BLMotor;

        FLMotor  = hardwareMap.get(DcMotor.class, "leftFront");
        FRMotor  = hardwareMap.get(DcMotor.class, "rightFront");
        BRMotor  = hardwareMap.get(DcMotor.class, "rightBack");
        BLMotor  = hardwareMap.get(DcMotor.class, "leftBack");

        FLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        FRMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        BRMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        BLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
         String test = "";
        int score = 0;



        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.setPipeline(pipeline);
        webcam.setMillisecondsPermissionTimeout(5000); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);

            }

            @Override
            public void onError(int errorCode) {}
        });


        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder Main = drive.actionBuilder(initialPose)

                .lineToY(1);

                while (!Objects.equals(pipeline.getResult(), "M") && score < 1000)
                {
                    if(Objects.equals(pipeline.getResult(), "R"))
                    {

                        FLMotor.setPower(-0.4 );
                        FRMotor.setPower(0.3 );
                        BLMotor.setPower(0.3 );
                        BRMotor.setPower(-0.3 );

                        test = "R";
                    }
                    if(Objects.equals(pipeline.getResult(), "L"))
                    {


                        FLMotor.setPower(0.4 );
                        FRMotor.setPower(-0.3 );
                        BLMotor.setPower(-0.3 );
                        BRMotor.setPower(0.3 );


                        test = "L";

                    }
                    if(Objects.equals(pipeline.getResult(), "M"))
                    {
                        FLMotor.setPower(0);
                        FRMotor.setPower(0);
                        BLMotor.setPower(0);
                        BRMotor.setPower(0);
                        test = "Center";
                        score++;


                    }
                }



        //CAM ------------------------------------------------------------------>

        TrajectoryActionBuilder Back = drive.actionBuilder(initialPose)

               .lineToY(-1);






        //CAM ------------------------------------------------------------------>


        Action MainAction = new SequentialAction(
                Main.build()


        );



//***********************************************************************************************************





        waitForStart();
        if (isStopRequested()) return;
        Actions.runBlocking(
                MainAction

        );

    }




}
