package ra.bstchecker

/**
 * @author Remus Amalinei
 */
case class Node(
                 value: Int,
                 left: Option[Node] = None,
                 right: Option[Node] = None)
