;1. For a given tree of type (1) return the path from the root node to a certain given node X.

;(A 2 B 0 C 2 D 0 E 0) (1) ->type 1
;
;
;
;
;

;goLeft ->returns the left subtree
(defun goLeft (L nrNodes nrEdges)
  (cond
   ((null l) nil)
   ((EQUAL nrNodes (+ 1 nrEdges)) nil)
   (T (list (car L) (cadr L) (goLeft (cddr L) (+ 1 nrNodes) (+ (cadr L) nrEdges))))
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


;pathToNode 
(defun pathToNode (L E)
  (cond 
   ((null L) nil)
   ((equal (car L) E) (list E))
   ((checkMemberLeftSubtree L E) (cons (car L) (pathToNode (getLeftSubtree L) E)))
   ((checkMemberRightSubtree L E) (cons  (car L) (pathToNode (getRightSubtree L) E)))
  )
)





