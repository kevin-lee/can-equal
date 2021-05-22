package canequal

/** @author Kevin Lee
  * @since 2021-05-19
  */
object options extends OptionInstances

trait OptionInstances {

  given canEqualOption[T](using eq: CanEqual[T, T]): CanEqual[Option[T], Option[T]] =
    CanEqual.derived // for `case None` in pattern matching

  given canEqualOptions[T, U](using eq: CanEqual[T, U]): CanEqual[Option[T], Option[U]] =
    CanEqual.derived

}
