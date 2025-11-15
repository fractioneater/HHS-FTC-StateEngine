package org.firstinspires.ftc.teamcode.hardware

import android.graphics.Color
import android.util.Size
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor
import org.firstinspires.ftc.vision.opencv.ColorRange
import org.firstinspires.ftc.vision.opencv.ImageRegion

// A couple of helpful subclasses.
enum class ArtifactColor {
  GREEN, PURPLE;

  override fun toString(): String {
    return if (this == PURPLE) "purple" else "green"
  }
}

class VisibleArtifact(val color: ArtifactColor, val blob: ColorBlobLocatorProcessor.Blob)

// The majority of this code is from samples/ConceptVisionColorLocator_Circle
class ArtifactCamera(private val rh: RobotHardware) : Hardware {
  private val greenLocator = ColorBlobLocatorProcessor.Builder()
    .setTargetColorRange(ColorRange.ARTIFACT_GREEN)
    .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
    .setRoi(ImageRegion.asUnityCenterCoordinates(-1.0, 1.0, 1.0, -1.0))
    .setDrawContours(true)
    .setBoxFitColor(0)
    .setCircleFitColor(Color.rgb(0, 255, 0))
    .setBlurSize(5)
    .setDilateSize(15)
    .setErodeSize(15)
    .setMorphOperationType(ColorBlobLocatorProcessor.MorphOperationType.CLOSING)
    .build()

  private val purpleLocator = ColorBlobLocatorProcessor.Builder()
    .setTargetColorRange(ColorRange.ARTIFACT_PURPLE)
    .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
    .setRoi(ImageRegion.asUnityCenterCoordinates(-1.0, 1.0, 1.0, -1.0))
    .setDrawContours(true)
    .setBoxFitColor(0)
    .setCircleFitColor(Color.rgb(200, 0, 255))
    .setBlurSize(5)
    .setDilateSize(15)
    .setErodeSize(15)
    .setMorphOperationType(ColorBlobLocatorProcessor.MorphOperationType.CLOSING)
    .build()

  private val aprilTag = AprilTagProcessor.Builder()
    .setTagLibrary(AprilTagGameDatabase.getDecodeTagLibrary())
    .setDrawTagID(true)
    .setDrawTagOutline(true)
    .setDrawAxes(false)
    .setDrawCubeProjection(true)
    .build()

  @Suppress("unused")
  private lateinit var portal: VisionPortal

  // --- PROPERTIES ---
  // Getter syntax: instead of calling camera.getArtifacts(), we can just write camera.artifacts and it will evaluate this code.

  val artifacts: List<VisibleArtifact>
    get() {
      val greenBlobs = greenLocator.blobs
      val purpleBlobs = purpleLocator.blobs

      // Filter by area of blob. We may want to adjust this range.
      ColorBlobLocatorProcessor.Util.filterByCriteria(ColorBlobLocatorProcessor.BlobCriteria.BY_CONTOUR_AREA, 50.0, 20_000.0, greenBlobs)
      ColorBlobLocatorProcessor.Util.filterByCriteria(ColorBlobLocatorProcessor.BlobCriteria.BY_CONTOUR_AREA, 50.0, 20_000.0, purpleBlobs)

      // Filter by how circular the blobs are. Shadows will affect this, so we'll need to test on each field.
      ColorBlobLocatorProcessor.Util.filterByCriteria(ColorBlobLocatorProcessor.BlobCriteria.BY_CIRCULARITY, 0.6, 1.0, greenBlobs)
      ColorBlobLocatorProcessor.Util.filterByCriteria(ColorBlobLocatorProcessor.BlobCriteria.BY_CIRCULARITY, 0.6, 1.0, purpleBlobs)

      return greenBlobs.map { it -> VisibleArtifact(ArtifactColor.GREEN, it) } +
        purpleBlobs.map { it -> VisibleArtifact(ArtifactColor.PURPLE, it) }
    }

  val aprilTags: List<AprilTagDetection>
    get() {
      return aprilTag.detections
    }

  val freshAprilTags: List<AprilTagDetection>
    get() {
      return aprilTag.freshDetections
    }

  // --- INTERFACE METHODS ---
  // These need to be defined here because this class implements Hardware.

  override fun initialize() {
    portal = VisionPortal.Builder()
      .addProcessors(greenLocator, purpleLocator, aprilTag)
      .setCameraResolution(Size(640, 480)) // Higher resolution is WORSE for color blob locators.
      .setCamera(rh.op.hardwareMap.get(WebcamName::class.java, "cam"))
      .build()

    aprilTag.setDecimation(3f)
  }

  override fun update() {
    // Nothing, really.
  }

  override fun telemetry() {
    val t = rh.op.telemetry
    t.addLine("vision\n----")

    t.addLine("AprilTags")
    for (tag in aprilTags) {
      if (tag.metadata == null) {
        t.addLine("\tid ${tag.id}, tag unknown, center (in pixels): ${tag.center.x} ${tag.center.y}")
      } else {
        t.addLine("\tid ${tag.id}, name: ${tag.metadata.name}, xyz: ${tag.ftcPose.x} ${tag.ftcPose.y} ${tag.ftcPose.z}")
      }
    }

    t.addLine("Artifacts")
    for (ball in artifacts) {
      val circle = ball.blob.circle
      t.addLine("\t${ball.color} artifact at (${circle.x}, ${circle.y}), radius: ${circle.radius}")
    }
  }
}