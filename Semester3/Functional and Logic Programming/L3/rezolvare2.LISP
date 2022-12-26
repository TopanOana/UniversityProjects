; 2. Write a function that returns the sum of numeric atoms in a list, at any level.

;mathematical model
; numeric_sum(l1...ln)
; = 0, n=0
; = l, n=1 l number
; = 0, n=1 l not number
; = numeric_sum(l1) + numeric_sum(l2) + ... + numeric_sum(ln)

(defun numeric_sum (L)
  (cond 
   ((null L) 0)
   ((numberp L) L)
   ((atom L) 0)
   (T (apply #'+ (mapcar #'(lambda(x)(numeric_sum x)) L)))
  )
)