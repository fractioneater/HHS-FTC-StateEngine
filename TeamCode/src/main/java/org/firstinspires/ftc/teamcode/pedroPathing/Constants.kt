package org.firstinspires.ftc.teamcode.pedroPathing

import com.pedropathing.follower.Follower
import com.pedropathing.follower.FollowerConstants
import com.pedropathing.ftc.FollowerBuilder
import com.pedropathing.ftc.drivetrains.MecanumConstants
import com.pedropathing.ftc.localization.Encoder
import com.pedropathing.ftc.localization.constants.DriveEncoderConstants
import com.pedropathing.paths.PathConstraints
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap

object Constants {
  var followerConstants = FollowerConstants().mass(7.0)!! // TODO!

  var pathConstraints: PathConstraints = PathConstraints(0.99, 100.0, 1.0, 1.0)

  @JvmStatic
  fun createFollower(hardwareMap: HardwareMap): Follower {
    return FollowerBuilder(followerConstants, hardwareMap)
      .pathConstraints(pathConstraints)
      .mecanumDrivetrain(driveConstants)
      .driveEncoderLocalizer(localizerConstants)
      .build()
  }

  var driveConstants: MecanumConstants? = MecanumConstants()
    .maxPower(1.0)
    .rightFrontMotorName("rfD")
    .rightRearMotorName("rbD")
    .leftRearMotorName("lbD")
    .leftFrontMotorName("lfD")
    .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
    .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
    .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
    .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)

  var localizerConstants: DriveEncoderConstants? = DriveEncoderConstants()
    .rightFrontMotorName("rfD")
    .rightRearMotorName("rbD")
    .leftRearMotorName("lbD")
    .leftFrontMotorName("lfD")
    .leftFrontEncoderDirection(Encoder.FORWARD)
    .leftRearEncoderDirection(Encoder.FORWARD)
    .rightFrontEncoderDirection(Encoder.FORWARD)
    .rightRearEncoderDirection(Encoder.FORWARD)
    .robotLength(12.0) // TODO: Confirm on Discord where I'm supposed to be measuring these from
    .robotWidth(16.0)
//    .forwardTicksToInches()
  // TODO NEXT: Forward tuner.
}