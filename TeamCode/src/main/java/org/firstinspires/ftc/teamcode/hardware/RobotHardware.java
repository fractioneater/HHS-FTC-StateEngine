package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware;
import org.firstinspires.ftc.teamcode.hardware.examples.ExampleClawHardware;
import org.firstinspires.ftc.teamcode.hardware.examples.ExampleDriveHardware;
import org.firstinspires.ftc.teamcode.hardware.examples.ExampleLiftHardware;

public class RobotHardware implements Hardware {

  public OpMode op;

  public ElapsedTime runtime;

  public Controls controls;
  public Hardware[] hardware;

  /* TODO
      This is where you will declare all your hardware objects:
      ex)
        public SampleHardware sampleH; */
  public ExampleClawHardware exampleClawH;
  public ExampleDriveHardware exampleDriveH;
  public ExampleLiftHardware exampleLiftH;

  public RobotHardware(OpMode op) {
    this.op = op;

    controls = new Controls(this);

    hardware = new Hardware[]{
      /* TODO
          Here you will setup all your hardware objects in this list. The list is here for the opmode to loop through.
          ex)
            sampleH = new SampleHardware(this), */
      exampleClawH = new ExampleClawHardware(this), exampleDriveH = new ExampleDriveHardware(this), exampleLiftH = new ExampleLiftHardware(this), };

    runtime = new ElapsedTime();
  }

  // Initialize all the hardware objects
  public void initialize() {
    controls.initialize();
    for (Hardware hw : hardware) {
      hw.initialize();
    }
  }

  // Update all the hardware objects so that they will have accurate data and update the target positions of motors, servos, etc.
  public void update() {
    for (Hardware hw : hardware) {
      hw.update();
    }
  }

  // Print out the telemetry data for each hardware object
  public void telemetry() {
    for (Hardware hw : hardware) {
      hw.telemetry();
      op.telemetry.addLine();
    }
  }
}
