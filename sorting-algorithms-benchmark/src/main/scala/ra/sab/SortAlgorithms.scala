package ra.sab

import scala.collection.mutable
import scala.util.Random

/**
  * @author Remus Amalinei
  */
object SortAlgorithms {

  def insertionSort(as: Array[Int]): Array[Int] = {
    as.indices.foreach { i =>
      val x = as(i)
      var j = i - 1
      while ((j >= 0) && (as(j) > x)) {
        as(j + 1) = as(j)
        j -= 1
      }
      as(j + 1) = x
    }

    as
  }

  def heapsort(as: Seq[Int]): Seq[Int] = {
    val priorityQueue = mutable.PriorityQueue[Int]()(Ordering[Int].reverse)

    priorityQueue.enqueue(as: _*)

    priorityQueue.dequeueAll
  }

  def partiallySortedArray(size: Int, sortedSize: Int): Array[Int] = {
    val as = mutable.ArraySeq(0 until size: _*)
    val randomIndices = Random.shuffle(as)

    var k = 0
    while (k < (size - sortedSize) / 2) {
      val i = randomIndices(2 * k)
      val j = randomIndices(2 * k + 1)

      val tmp = as(i)
      as(i) = as(j)
      as(j) = tmp

      k += 1
    }

    as.toArray
  }
}
