; 4. Convert a tree of type (2) to type (1).


;type 2: (A (B) (C (D) (E)))
;type 1: (A 2 B 0 C 2 D 0 E 0)


;appendLists 
(defun appendLists (L1 L2)
  (cond 
   ((null L1) L2)
   ((null L2) L1)
   (T (cons (car L1) (appendLists (cdr L1) L2)))
  )
)



;convert21 
(defun convert21 (L)
  (cond 
   ((null L) nil)
   (T (cond 
    ((and (NULL (CADDR L)) (null (cadr L))) (list (car L) 0))
    ((null (caddr L)) (APPENDLISTS (list (car L) 1) (convert21 (cadr L))))
    (T (APPENDLISTS (LIST (car L)  2) (APPENDLISTS (convert21 (cadr L)) (convert21 (caddr L)))))
    ))
   )
)
