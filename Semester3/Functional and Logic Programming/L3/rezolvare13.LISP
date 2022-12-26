; 13. Define a function that returns the depth of a tree represented as 
;(root list_of_nodes_subtree1 ... list_of_nodes_subtreen)
;Eg. the depth of the tree (a (b (c)) (d) (e (f))) is 3


;mathematical model
; depth_tree(l1...ln, d)
; = 0, n=0
; = d, n=1
; = depth_tree(l1,d) U depth_tree(l2, d) U ... U depth_tree(ln, d), otherwise

(defun depth_tree (L D)
  (cond 
   ((null L) 0)
   ((atom L) D)
   (T (mapcar #'(lambda(x)(depth_tree x (+ D 1))) L))
  )
)


;mathematical model
; max_nonlinear (l1...ln, m)
; = m, n=0
; = max_nonlinear (l2...ln, l1), l1>m and n>0 and l1 - atom
; = max_nonlinear (l2...ln, m), l1-atom  m>l1 n>0
; = max_nonlinear (l2...ln, max_nonlinear (l11..l1m, m)), if l1 is a list

(defun max_nonlinear (L M)
  (cond 
   ((null L) M)
   ((and (atom (car L)) (> (car L) M)) (max_nonlinear (cdr L) (car L)))
   ((atom (car L)) (max_nonlinear (cdr L) M))
   (T (max_nonlinear (cdr L) (max_nonlinear (car L) M)))
  )
)

(defun final (L) 
  (setq List (depth_tree L 0))
  (max_nonlinear List 0)
)