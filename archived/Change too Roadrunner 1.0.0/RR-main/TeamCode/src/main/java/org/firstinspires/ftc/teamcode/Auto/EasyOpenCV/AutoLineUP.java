package org.firstinspires.ftc.teamcode.Auto.EasyOpenCV;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.bosch.BNO055IMU;


import java.util.List;
import java.util.Objects;

@TeleOp(name = "AutoLineUP")
public class AutoLineUP extends OpMode {
    OpenCvWebcam webcam;
//    OpenCVBlue pipeline = new OpenCVBlue(telemetry);

    private boolean isStreaming = false; // Flag to track streaming state

    private DcMotor FLMotor, FRMotor, BRMotor, BLMotor;


    @Override
    public void init(){
        FLMotor  = hardwareMap.get(DcMotor.class, "leftFront");
        FRMotor  = hardwareMap.get(DcMotor.class, "rightFront");
        BRMotor  = hardwareMap.get(DcMotor.class, "rightBack");
        BLMotor  = hardwareMap.get(DcMotor.class, "leftBack");

        FLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        FRMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        BRMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        BLMotor.setDirection(DcMotorSimple.Direction.REVERSE);



        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
      //  webcam.setPipeline(pipeline);
        webcam.setMillisecondsPermissionTimeout(5000); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
                isStreaming = true; // Set the flag when streaming starts
            }

            @Override
            public void onError(int errorCode) {}
        });

    }



    @Override
    public void init_loop(){
        //empty
    }//end of init loop


    @Override
    public void start(){
        //empty
    }//end of start

    public String test = "";



    public void Lineup()
    {

//        if(Objects.equals(pipeline.getResult(), "R"))
        {

            FLMotor.setPower(-0.4 );
            FRMotor.setPower(0.3 );
            BLMotor.setPower(0.3 );
            BRMotor.setPower(-0.3 );

            test = "R";
        }
//        if(Objects.equals(pipeline.getResult(), "L"))
        {


            FLMotor.setPower(0.4 );
            FRMotor.setPower(-0.3 );
            BLMotor.setPower(-0.3 );
            BRMotor.setPower(0.3 );


            test = "L";

        }
//        if(Objects.equals(pipeline.getResult(), "M"))
        {
            FLMotor.setPower(0);
            FRMotor.setPower(0);
            BLMotor.setPower(0);
            BRMotor.setPower(0);
            test = "Center";


//            if (isStreaming) {
//                webcam.stopStreaming();
//                webcam.closeCameraDevice();
//                isStreaming = false; // Reset the flag when streaming stops
//            }
        }




    }
    @Override
    public void loop() {

        Lineup();





        telemetry.addLine(test);
        telemetry.update();
    }





    @Override
    public void stop()
    {
        FLMotor.setPower(0);
        FRMotor.setPower(0);
        BLMotor.setPower(0);
        BRMotor.setPower(0);

        // Stop streaming if the camera is currently streaming
        if (isStreaming) {
            webcam.stopStreaming();
            webcam.closeCameraDevice();
            isStreaming = false; // Reset the flag when streaming stops
        }



    }//end of stop
}
