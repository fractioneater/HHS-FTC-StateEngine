package org.firstinspires.ftc.teamcode.stateengine

class ParallelStack(private val stack: Array<State>) : StackList(stack), State {
  override var isDone: Boolean = false

  override fun init() {
    super.initStack()
  }

  override fun run() {
    if (this.isDone) return
    var done = true

    for (state in stack) {
      if (!state.isDone) {
        state.run()
        done = false
      }
    }

    // When all of the states are done, this stack will be done
    if (done) {
      this.isDone = true
    }
  }

  override fun stop() {
    this.isDone = true
  }
}
