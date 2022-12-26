;8. Return the list of nodes of a tree of type (2) accessed inorder
;(A (B) (C (D) (E))) ->type 2
;left subtree  -> cadr L
;right subtree -> caddr L 


;appendLists 
(defun appendLists (L P)
  (cond 
   ((NULL L) P)
   ((NULL P) L)
   (T (CONS (CAR L) (appendLists (CDR L) P)))
  )
)

(defun inorderTraversal (L)
  (cond 
   ((NULL L) NIL)
   ((NULL (CADR L)) (LIST (CAR L)))
   ((NULL (CADDR L)) (CONS (inorderTraversal (CADR L)) (CAR L)))
   (T (appendLists (inorderTraversal (CADR L)) (CONS (CAR L) (inorderTraversal (CADDR L)))))
  )
)