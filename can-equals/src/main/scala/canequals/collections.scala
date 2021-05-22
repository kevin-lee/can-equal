package canequals

/** @author Kevin Lee
  * @since 2021-05-21
  */
object collections extends CollectionInstances

trait CollectionInstances {

  given canEqualSeq[T](using eq: CanEqual[T, T]): CanEqual[Seq[T], Seq[T]] = CanEqual.derived

}
