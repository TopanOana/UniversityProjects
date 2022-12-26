;7. Return the level of a node X in a tree of type (1). The level of the root element is 0.



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


N;checkMemberLeftSubtree
(defun checkMemberLeftSubtree (L E)
  (cond 
   ((equal (member E (getLeftSubtree L)) NIL) NIL)
   (T)
   )
)


;checkMemberRightSubtree
(defun checkMemberRightSubtree (L E)
  (cond
   ((equal (member E (getRightSubtree L)) NIL) NIL)
   (T)
   )
)


(defun levelNode (L X)
  (cond 
   ((NULL L) NIL)
   ((EQUAL (CAR L) X) 0)
   ((checkMemberLeftSubtree L X) (+ 1 (levelNode (getLeftSubtree L) X)))
   ((checkMemberRightSubtree L X) (+ 1 (levelNode (getRightSubtree L) X)))
  )
)