;1. For a given tree of type (1) return the path from the root node to a certain given node X.

;(A 2 B 0 C 2 D 0 E 0) (1) ->type 1
;
;
;
;
;

;goLeft ->returns the left subtree
;goLeft(L:list, nrNodes:number, nrEdges:number)
;mathematical model:
; goLeft (l1l2..ln, nrNodes, nrEdges) =
;	= (), if n=0
;	= (), if nrNodes = nrEdges+1
;	= l1 U l2 U goLeft(l3..ln, nrNodes+1, nrEdges+l2), otherwise
(defun goLeft (L nrNodes nrEdges)
  (cond
   ((null l) nil)
   ((EQUAL nrNodes (+ 1 nrEdges)) nil)
   (T (cons (car L) (cons (cadr L) (goLeft (cddr L) (+ 1 nrNodes) (+ (cadr L) nrEdges)))))
  )
)



;getLeftSubTree
;getLeftSubTree(L:list)
;mathematical model: 
; getLeftSubTree(l1l2..ln)
;	= goLeft(l3..ln, 0,0)
(defun getLeftSubtree (L)
  (goLeft (cddr L) 0 0)
)


;goRight -> returns the right subtree
;goRight(L:list, nrNodes:number, nrEdges:number)
;mathematical model:
; goRight(l1...ln, nrNodes, nrEdges)
; 	= (), if n=0
; 	= l1...ln, if nrNodes == nrEdges+1
;	= goRight(l3...ln), otherwise
(defun goRight (L nrNodes nrEdges)
  (cond
   ((null L) nil)
   ((EQUAL nrNodes (+ 1 nrEdges)) L)
   (T (goRight (cddr L) (+ 1 nrNodes) (+ (cadr L) nrEdges)))
  )
)


;getRightSubtree
;getRightSubTree(L:list)
;mathematical model: 
; getRightSubTree(l1l2..ln)
;	= goRight(l3..ln, 0,0)
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





