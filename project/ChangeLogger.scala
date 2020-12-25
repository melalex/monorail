import java.time.LocalDate

import org.fusesource.scalate._

import scala.reflect.io.Path
import scala.util.matching._

object ChangeLogger {

  val BreakingChangeRegEx: Regex = "BREAKING CHANGE|!" r
  val FeatureRegEx: Regex        = "^feat\\(?.*\\)?!?: .*" r
  val FixRegEx: Regex            = "^fix\\(?.*\\)?!?: .*" r
  val RefactoringRegEx: Regex    = "^refactor\\(?.*\\)?!?: .*" r

  private val PrefixRegEx = "^(feat|fix|refactor)\\(?.*\\)?!?:" r

  private val engine = new TemplateEngine

  def generateChangelogString(template: Path, version: String, date: LocalDate, commitMsgs: Seq[String]): String = {
    val commits = commitMsgs
      .map(mapToCommit)
      .groupBy(_ commitType)
      .mapValues(msgs => msgs.map(_ tittle))
      .withDefaultValue(Seq())

    val parameters = Map(
      "version" -> version,
      "date"    -> date,
      "added"   -> commits(CommitType.Feature),
      "fixed"   -> commits(CommitType.Fix),
      "changed" -> commits(CommitType.Refactoring)
    )

    engine.layout(template.path, parameters)
  }

  private def mapToCommit(commitMsg: String): Commit = Commit(
    commitMsg,
    getTittle(commitMsg),
    getCommitType(commitMsg)
  )

  private def getTittle(commitMsg: String) =
    PrefixRegEx.replaceAllIn(commitMsg.takeWhile(_ != '\n'), "")

  private def getCommitType(commitMsg: String): CommitType.CommitType = commitMsg match {
    case FeatureRegEx(_*)     => CommitType.Feature
    case FixRegEx(_*)         => CommitType.Fix
    case RefactoringRegEx(_*) => CommitType.Refactoring
    case _                    => CommitType.Unknown
  }

  private case class Commit(
      fullMessage: String,
      tittle: String,
      commitType: CommitType.CommitType
  )

  private object CommitType extends Enumeration {
    type CommitType = Value
    val Feature, Fix, Refactoring, Unknown = Value
  }
}
