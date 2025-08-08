/*
 * Copyright (c) 2021 Kallen, Curtis, Aaron
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package org.firstinspires.ftc.teamcode.Auto;

//import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

//@I2cDeviceType()
//@TeleOp

public class OpenCVCubeBLUE extends OpenCvPipeline {

    private volatile String result = "start";

    /*
     * These are our variables that will be
     * modifiable from the variable tuner.
     *
     * Scalars in OpenCV are generally used to
     * represent color. So our values in the
     * lower and upper Scalars here represent
     * the Y, Cr and Cb values respectively.
     *
     * YCbCr, like most color spaces, range
     * from 0-255, so we default to those
     * min and max values here for now, meaning
     * that all pixels will be shown.
     */
    //public String result = "";
    private Scalar lower = new Scalar(96.3f, 73.7f, 0);
    private Scalar upper = new Scalar(147.3f, 255, 199.8f);
    /**
     * This will allow us to choose the color
     * space we want to use on the live field
     * tuner instead of hardcoding it
     */
    private ColorSpace colorSpace = ColorSpace.HSV;

    /*
     * A good practice when typing EOCV pipelines is
     * declaring the Mats you will use here at the top
     * of your pipeline, to reuse the same buffers every
     * time. This removes the need to call mat.release()
     * with every Mat you create on the processFrame method,
     * and therefore, reducing the possibility of getting a
     * memory leak and causing the app to crash due to an
     * "Out of Memory" error.
     *
     */
    Mat hsvMat = new Mat();
    Mat mask = new Mat();

    private Mat Mats       = new Mat();

    Mat labels = new Mat();
    Mat stats = new Mat();
    Mat centroids = new Mat();
    Mat region = new Mat();
    Mat redDotMask = new Mat();
    int squareSize = 25; //newchange

    List<Rectangle> rectangles = new ArrayList<>();

    List<RadCords> RadCords = new ArrayList<>();


    private Telemetry telemetry = null;

    /**
     * Enum to choose which color space to choose
     * with the live variable tuner isntead of
     * hardcoding it.
     */
    enum ColorSpace {
        /*
         * Define our "conversion codes" in the enum
         * so that we don't have to do a switch
         * statement in the processFrame method.
         */
        RGB(Imgproc.COLOR_RGBA2RGB),
        HSV(Imgproc.COLOR_RGB2HSV),
        YCrCb(Imgproc.COLOR_RGB2YCrCb),
        Lab(Imgproc.COLOR_RGB2Lab);



        //store cvtCode in a public var
        public int cvtCode = 0;

        //constructor to be used by enum declarations above
        ColorSpace(int cvtCode) {
            this.cvtCode = cvtCode;
        }
    }



    int centerX;
    int centerY;
    double distanceThreshhold;



    public int Boxnum = 1;










    int centerylim = 1000000000;
    public OpenCVCubeBLUE(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public String getResult()
    {
        return result ;
    }

    public Mat processFrame(Mat input) {
        Mats       = new Mat();


        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);


        Core.inRange(hsvMat, lower, upper, mask);


        Core.bitwise_and(input, input, Mats, mask);
        RadCords.clear();
        rectangles.clear();

        int GH = input.rows();
        int GW = input.cols();


        int V = (int) Math.ceil((double) GH / squareSize);
        int H = (int) Math.ceil((double) GW / squareSize);

        int newhight = GH / V;
        int newwidth = GW / H;



        for (int y = 0; y < GH; y += newhight) {
            for (int x = 0; x < GW; x += newwidth) {
                int rectWidth = Math.min(newwidth, GW - x);
                int rectHeight = Math.min(newhight, GH - y);

                rectangles.add(new Rectangle(x, y, rectWidth, rectHeight));
            }
        }



        for (int i = 0; i < rectangles.size(); i++)
        {

            Rect roi = rectangles.get(i).toOpenCVRect();
            region = new Mat(Mats, roi);



            Mat hsvRegion = new Mat();
            Imgproc.cvtColor(region, hsvRegion, Imgproc.COLOR_RGB2HSV);









            Mat blueMask = new Mat();
            Core.inRange(hsvRegion, lower, upper, blueMask);



            int totalPixels = roi.width * roi.height;
            int bluePixels = Core.countNonZero(blueMask);






            double bluePercentage = (double) bluePixels / totalPixels * 100;


            centerX = roi.x + roi.width / 2;
            centerY = roi.y + roi.height / 2;




            if (bluePercentage > 99) {

                RadCords.add(new RadCords(centerX, centerY));

            }
            blueMask.release();
            hsvRegion.release();


        }



        Mat redDotMask = Mat.zeros(Mats.size(), CvType.CV_8UC1);

        for (int j = 0; j < RadCords.size(); j++) {
            double CX = RadCords.get(j).X();
            double CY = RadCords.get(j).Y();
            Imgproc.circle(redDotMask, new Point(CX, CY), 5, new Scalar(255), -1);
        }


        //creates shapes with the red color:
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(15, 15));

        Imgproc.dilate(redDotMask, redDotMask, kernel);

        //new labels





        //number of conncted components.
        int nLabels = Imgproc.connectedComponentsWithStats(redDotMask, labels, stats, centroids);





        List<Point> LowPos = new ArrayList<>();
        //this finds the bottom box
        for (int i = 1; i < nLabels; i++) {
            double x = stats.get(i, Imgproc.CC_STAT_LEFT)[0];
            double y = stats.get(i, Imgproc.CC_STAT_TOP)[0];
            double width = stats.get(i, Imgproc.CC_STAT_WIDTH)[0];
            double height = stats.get(i, Imgproc.CC_STAT_HEIGHT)[0];


            double lowestY = y + height / 2;


            double centerX = x + width / 2;


            LowPos.add(new Point(centerX, lowestY));
        }





        int count = 0;
        //mark
        for (Point position : LowPos) {
            count++;


            if(Boxnum == (int)count)
            {
                Imgproc.circle(Mats, position, 10, new Scalar(0, 255, 0), -1);
            }

        }










        telemetry.addData("Total boxes", rectangles.size());
        telemetry.addData("Total Shapes", LowPos.size());

        telemetry.addData("Use gamepad ↑↓ to cycle up and down", null);
        telemetry.addData("Detecting", Boxnum);

        telemetry.update();





        if(rectangles.size() > 50)
        {
            rectangles.clear();
        }


        redDotMask.release();
        labels.release();
        stats.release();
        centroids.release();

        labels.release();
        stats.release();
        centroids.release();
        return Mats;
    }




    static class Rectangle{
        int x, y, width, height;

        Rectangle(int x, int y, int width, int height)
        {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }


        public Rect toOpenCVRect() {
            return new Rect(x, y, width, height);
        }
    }



    static class RadCords{
        int x, y;

        RadCords(int x, int y)
        {
            this.x = x;
            this.y = y;

        }

        public int X() {
            return x;

        }

        public int Y() {
            return y;

        }
    }

}