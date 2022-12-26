; 3. Define a function to tests the membership of a node in a n-tree 
; represented as (root list_of_nodes_subtree1 ... list_of_nodes_subtreen)
; Eg. tree is (a (b (c)) (d) (E (f))) and the node is "b" => true



;mathematical model
; member_of_tree(l1...ln, e)
; = (), n=0
; = T, l=e n=1
; = nil, l!=e n=1
; = member_of_tree(l1 e) U member_of_tree(l2, e) U ... U member_of_tree(ln, e), otherwise

(defun member_of_tree (L E)
  (cond 
   ((null L) NIL)
   ((atom L) (EQUAL L E))
   (T (mapcar #'(lambda(x)(member_of_tree x E)) L))
  )
)


;mathematical model
; or_on_list(l1...ln)
; = nil, n=0
; = T, l1 true
; = nil, l1 false
; = or_on_list (l2...ln) or or_on_list(l1), where l1 is a list

(defun or_on_list (L)
  (cond 
   ((null L) NIL)
   ((and (atom (car L)) (equal (car L) T)))
   (T (or (or_on_list (cdr L)) (or_on_list (car L))))
  )
)

(defun check_if_member_of_tree (L E)
  (or_on_list (member_of_tree L E))
)