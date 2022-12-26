; 15. Write a function that reverses a list together with all its sublists elements, at any level

;reverse linear 
;mathematical model
; reverse_aux(l1...ln)
;  = (), n=0
;  = append ( reverse_aux(l2...ln) , l1), otherwise
(defun reverse_aux (L)
  (cond 
   ((null L) NIL)
   (T (append (reverse_aux (cdr L)) (list (car L))))
  )
)


;mathematical model
; reverse_list(l1...ln)
; = (), n=0
; = l1, n=1
; = reverse_aux ( reverse_list(l1) U reverse_list(l2) U ... U reverse_list(ln)), otherwise

(defun reverse_list (L)
  (cond 
   ((null L) NIL)
   ((atom L) L)
   (T (apply #'reverse_aux (list (mapcar #'(lambda(x) (reverse_list x)) L))))
  )
)
