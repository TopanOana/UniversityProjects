; 4. Write a function that returns the product of numeric atoms in a list, at any level.

;mathematical model
; product_list(l1...ln)
; = 1, n=0
; = l, n=1 and l number
; = 1, n=1 and l not number
; = product_list(l1) * product_list(l2) * ... * product_list(ln), otherwise


(defun product_list (L)
  (cond 
   ((null L) 1)
   ((numberp L) L)
   ((atom L) 1)
   (T (apply #'* (mapcar #'(lambda(x) (product_list x)) L)))
  )
)