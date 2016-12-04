package ra.sab

import org.scalatest.prop.TableDrivenPropertyChecks.forAll
import org.scalatest.prop.Tables.Table
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Remus Amalinei
  */
class SortAlgorithmsTest extends FlatSpec with Matchers {

  "Insertion sort" should "order integers ascending" in {
    forAll(sortExpectations) { (unsortedArray, expectedSortedArray) =>
      SortAlgorithms.insertionSort(unsortedArray) should be(expectedSortedArray)
    }
  }

  "Heapsort" should "order integers ascending" in {
    forAll(sortExpectations) { (unsortedArray, expectedSortedArray) =>
      SortAlgorithms.heapsort(unsortedArray) should be(expectedSortedArray)
    }
  }

  "An array" should "have a number of elements sorted and in the correct position" in {
    val expectations = Table(
      ("size", "sortedSize", "expectedSortedSize"),

      (100, 0, 0),
      (100, 100, 100),
      (17, 4, 5),
      (17, 3, 3),
      (16, 4, 4),
      (16, 3, 4)
    )
    forAll(expectations) { (size, sortedSize, expectedSortedSize) =>
      val as = SortAlgorithms.partiallySortedArray(size, sortedSize)

      val actualSortedSize = as.indices.count(i => as(i) == i)

      expectedSortedSize should be(actualSortedSize)
    }
  }

  val sortExpectations = Table(
    ("unsortedArray", "expectedSortedArray"),

    (Array[Int](), Array[Int]()),
    (Array(1), Array(1)),
    (Array(1, 2, 3, 4, 5, 6, 7), Array(1, 2, 3, 4, 5, 6, 7)),
    (Array(7, 6, 5, 4, 3, 2, 1), Array(1, 2, 3, 4, 5, 6, 7)),
    (Array(4, 2, 1, 5, 7, 6, 3), Array(1, 2, 3, 4, 5, 6, 7))
  )
}
