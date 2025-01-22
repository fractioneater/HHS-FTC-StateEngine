package org.firstinspires.ftc.teamcode.teamcode.hardware.examples;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.teamcode.hardware.*;
import org.firstinspires.ftc.teamcode.teamcode.hardware.basicfunctionality.Hardware;

public class ExampleDriveHardware implements Hardware {

    private RobotHardware rh = null;

    // Declare motors
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;


    // directional inputs for telemetry
    private double axial = 0.0;
    private double lateral = 0.0;
    private double yaw = 0.0;
    private double maximum = 1.0;

    public ExampleDriveHardware(RobotHardware rh) {
        this.rh = rh;
    }

    public void initialize() {

        // init motors
        leftFrontDrive  = rh.op.hardwareMap.get(DcMotor.class, "lfD");
        leftBackDrive   = rh.op.hardwareMap.get(DcMotor.class, "lbD");
        rightFrontDrive = rh.op.hardwareMap.get(DcMotor.class, "rfD");
        rightBackDrive  = rh.op.hardwareMap.get(DcMotor.class, "rbD");

        // set motor directions
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    public void setPower(double powerLF, double powerRF, double powerLB, double powerRB) {
        leftFrontDrive.setPower(powerLF);
        rightFrontDrive.setPower(powerRF);
        leftBackDrive.setPower(powerLB);
        rightBackDrive.setPower(powerRB);
    }

    public void driveSmooth(double axial, double lateral, double yaw, double maximum) {

        // uses the smoothed inputs for driving
        drive(
                rh.controls.smoothInput( axial ),
                rh.controls.smoothInput( lateral ),
                rh.controls.smoothInput( yaw ),
                                        maximum
        );
    }

    public void drive(double axial, double lateral, double yaw, double maximum) {
        this.axial = axial;
        this.lateral = lateral;
        this.yaw = yaw;
        this.maximum = maximum;

        // individual power for each wheel combining each input
        double leftFrontPower  =  - axial + lateral + yaw;
        double rightFrontPower =  - axial - lateral - yaw;
        double leftBackPower   =  - axial - lateral + yaw;
        double rightBackPower  =  - axial + lateral - yaw;

        // Finds the maximum primitive power   (can go as large as 3.0)
        double scaler = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        scaler = Math.max(scaler, Math.abs(leftBackPower));
        scaler = Math.max(scaler, Math.abs(rightBackPower));

        // Checks if any power exceeds 1.0
        if (scaler > 1.0) {

            // Divides power by the maximum power so that no power exceeds 1.0
            leftFrontPower  /= scaler;
            rightFrontPower /= scaler;
            leftBackPower   /= scaler;
            rightBackPower  /= scaler;
        }

        // Scales the powers to be relative to the maximum speed
        leftFrontPower *= maximum;
        rightFrontPower *= maximum;
        leftBackPower *= maximum;
        rightBackPower *= maximum;

        // sets wheel power
        setPower(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);
    }

    public void update() {}

    public void telemetry() {

        rh.op.telemetry.addLine("Drive Hardware:");

        rh.op.telemetry.addLine();

        rh.op.telemetry.addData("axial", axial);
        rh.op.telemetry.addData("lateral", lateral);
        rh.op.telemetry.addData("yaw", yaw);

        rh.op.telemetry.addLine();

        rh.op.telemetry.addData("Maximum Power", maximum);

        rh.op.telemetry.addLine();

        rh.op.telemetry.addData("lf", leftFrontDrive.getPower());
        rh.op.telemetry.addData("lr", rightFrontDrive.getPower());
        rh.op.telemetry.addData("bf", leftBackDrive.getPower());
        rh.op.telemetry.addData("br", rightBackDrive.getPower());
    }
}
