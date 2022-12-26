;11. Write a function to determine the depth of a list.

;mathematical model
;depth (l1...ln, d)
; = 0, n=0
; = d, n=1
; = depth(l1, d) U depth(l2, d+1) U... U depth (ln, d+m)
(defun depth (L D)
  (cond
   ((null L) 0)
   ((atom L) D)
   (T (mapcar #'(lambda(x) (depth x (+ D 1))) L))
  )
)

;mathematical model
; max_list (l1...ln, m)
; = m, n=0
; = max_list (l2...ln, l1), l1>m n>0
; = max_list (l2...ln, m), m>l1  n>0
; = max_liat (l2...ln, max_list(l1, m)), l1 is a list
(defun max_list (L M)
  (cond
   ((null L) M)
   ((and (atom (car L)) (> (car L) M)) (max_list (cdr L) (car L)))
   ((atom (car L)) (max_list (cdr L) M))
   (T (max_list (cdr L) (max_list (car L) M)))
  )
)


(defun final (L)
  (setq List (depth L 0))
  (max_list List 0)
)