package ra.bstchecker

import org.scalatest.prop.TableDrivenPropertyChecks.{Table, forAll}
import org.scalatest.{FlatSpec, Matchers}

/**
 * @author Remus Amalinei
 */
class BstCheckerTest extends FlatSpec with Matchers {

  "A binary tree" should "be considered BST" in {
    val bsts = Table("t",
      Node(1),
      Node(5,
        Some(Node(2)),
        Some(Node(9))),
      Node(5,
        Some(Node(2,
          Some(Node(1)),
          Some(Node(3)))),
        Some(Node(9,
          Some(Node(7)),
          Some(Node(11))))))

    forAll(bsts) { t =>
      BstChecker.check(t) should be(true)
    }
  }

  it should "not be considered BST" in {
    val nonBsts = Table("t",
      Node(5,
        left = Some(Node(7))),
      Node(5,
        Some(Node(2)),
        Some(Node(9,
          left = Some(Node(1))))))

    forAll(nonBsts) { t =>
      BstChecker.check(t) should be(false)
    }
  }
}
