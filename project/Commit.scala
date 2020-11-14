import CommitType.CommitType

case class Commit(
    fullMessage: String,
    tittle: String,
    commitType: CommitType
)

object CommitType extends Enumeration {
  type CommitType = Value
  val Feature, Fix, Refactoring = Value
}
