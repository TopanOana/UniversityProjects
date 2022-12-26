; 6. Write a function that returns the maximum of numeric atoms in a list, at any level.

;mathematical model
; max_list(l1...ln)
; = 0, n=0
; = l1, n=1 and l1 number
; = 0, n=1 and l1 not number
; = max( max_list(l1), max_list(l2), ... , max_list(ln)), otherwise


(defun max_list (L)
  (cond 
   ((null L) 0)
   ((numberp L) L)
   ((atom L) 0)
   (T (apply #'MAX (mapcar #'(lambda(x) (max_list x)) L)))
   )
)