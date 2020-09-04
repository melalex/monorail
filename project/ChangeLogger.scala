object ChangeLogger {

  type IO = () => _

  def writeChangelog(): IO = () => {
    print("")
  }
}
