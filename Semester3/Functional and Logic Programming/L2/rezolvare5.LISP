;5. Return the level (depth) of a node in a tree of type (1). The level of the root element is 0.


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


;checkMemberLeftSubtree
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


;depthNode 
(defun depthNode (L E)
  (cond 
   ((null L) NIL)
   ((equal (car L) E) 0)
   ((checkMemberLeftSubtree L E) (+ 1 (depthNode (getLeftSubtree L) E)))
   ((checkMemberRightSubtree L E) (+ 1 (depthNode (getRightSubtree L) E)))
  )
)