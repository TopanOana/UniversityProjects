;2. Return the list of nodes on the k-th level of a tree of type (1).

;goLeft ->returns the left subtree
(defun goLeft (L nrNodes nrEdges)
  (cond
   ((null l) nil)
   ((EQUAL nrNodes (+ 1 nrEdges)) nil)
   (T (cons (car L) (cons (cadr L) (goLeft (cddr L) (+ 1 nrNodes) (+ (cadr L) nrEdges)))))
  )
)


;getLeftSubTree
(defun getLeftSubtree (L)
  (goLeft (cddr L) 0 0)
)


;goRight -> returns the right subtree
(defun goRight (L nrNodes nrEdges)
  (cond
   ((null L) nil)
   ((EQUAL nrNodes (+ 1 nrEdges)) L)
   (T (goRight (cddr L) (+ 1 nrNodes) (+ (cadr L) nrEdges)))
  )
)


;getRightSubtree
(defun getRightSubtree (L)
  (goRight (cddr L) 0 0)
)

;appendLists 
(defun appendLists (L1 L2)
  (cond 
   ((null L1) L2)
   ((null L2) L1)
   (T (cons (car L1) (appendLists (cdr L1) L2)))
  )
)


(defun k_level_nodes (L K)
  (cond 
   ((null L) NIL)
   ((EQUAL K 0) (List (car L)))
   (T (appendLists (k_level_nodes (getLeftSubtree L) (- K 1)) (k_level_nodes (getRightSubtree L) (- K 1))))
  )
)

