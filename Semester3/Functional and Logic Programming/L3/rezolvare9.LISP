; 9. Write a function that removes all occurrences of an atom from any level of a list.

;mathematical model
; aux_remove(l1...ln)
; = (), n=0
; = aux_remove(l2...ln), l1=nil
; = l1 U aux_remove(l2...ln), l1 atom
;= aux_remove(l1) U aux_remove(l2...ln), otherwise

(defun aux_remove (L)
  (cond 
   ((null L) nil)
   ((and (atom (car L)) (equal (car L) nil)) (aux_remove (cdr L)))
   ((atom (car L)) (cons (car L) (aux_remove (cdr L))))
   (T (cons (aux_remove (car L)) (aux_remove (cdr L))))
  )
)

;mathematical model
; remove_occ (e l1...ln)
; = nil, l=e 
; = l, n=1
; = aux_remove( remove_occ(e l1) U remove_occ(e l2) U ... U remove_occ(e ln)), otherwise
(defun remove_occ (E L)
  (cond 
   ((and (atom L) (equal L E)) NIL)
   ((atom L) L)
   (T (apply #'aux_remove (list (mapcar #'(lambda(x) (remove_occ E x)) L))))
  )
)