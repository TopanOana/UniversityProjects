;11. Return the level (and coresponded list of nodes)
; with maximum number of nodes for a tree of type (2). 
; The level of the root element is 0.
; (CAR L) -> root
; (CADR L) -> left subtree
; (CADDR L) -> right subtree
;number of nodes on a level


; example : L = (A (B) (C (D) (E)))
; level 0 - 1
; level 1 - 2
; level 2 - 2
; return 1 and (B C)

; HEIGHT(L,Counter)
; mathematical model : 
; HEIGHT(l1L2L3, Counter) = Counter, if n=0
;                          = MAX(HEIGHT(L2, 1+Counter), HEIGHT(L3, 1+Counter), otherwise; 

(DEFUN HEIGHT (L Counter)
  (COND 
   ((NULL L) Counter)
   (T (MAX (HEIGHT (CADR L) (+ 1 Counter)) (HEIGHT (CADDR L) (+ 1 Counter))))
  )
)


; GET_LENGTH(L, Length)
; returns the length of a list
; mathematical model:
; GET_LENGTH(l1...ln, Length) = Length, if n=0
;                             = GET_LENGTH(l2...ln, Length+1), otherwise

(DEFUN GET_LENGTH (L Length)
  (COND 
   ((NULL L) Length)
   (T (GET_LENGTH (CDR L) (+ 1 Length)))
  )
)


;another append 
; mathematical model:
; APPEND(l1...ln, p1...pm) = L, if m=0
;                          = P, if n=0
;                          = l1 U APPEND(l2...ln,p1...pn), otherwise 
(DEFUN APPEND_LISTS (L1 L2)
  (COND 
   ((NULL L1) L2)
   ((NULL L2) L1)
   (T (CONS (CAR L1) (APPEND (CDR L1) L2)))
  )
)



; GET_LEVEL(L Level)
; mathematical model:
; GET_LEVEL(L1L2L3, Level) = L1, if Level=0
;                          = (), if L is null
;                          = APPEND_LISTS(GET_LEVEL(L2, Level-1) U GET_LEVEL(L3, Level-1)), if L3 is not null and Level>0
;                          = GET_LEVEL(L2, Level-1), if L2 is not null and Level>0
;                          = (), otherwise

(DEFUN GET_LEVEL (L Level)
  (COND
   ((NULL L) ())
   ((EQL 0 Level) (LIST (CAR L)))
   ((> Level 0) 
    (COND 
     ((NOT (NULL (CADDR L))) (APPEND_LISTS (GET_LEVEL (CADR L) (- Level 1)) (GET_LEVEL (CADDR L) (- Level 1))))
     ((NOT (NULL (CADR L))) (GET_LEVEL (CADR L) (- Level 1)))
     (T ())
     )
    )
   )
)


; GET_MAX_LEVEL(L Aux_level Height_tree Max_nodes List_max_nodes)
; mathematical model: 
; GET_MAX_LEVEL(l1l2l3, Aux_level, Height_tree, Max_nodes, List_max_nodes) 
;   = List_max_nodes , if Aux_level = height tree
;   = GET_MAX_LEVEL(L, Aux_level+1, Height_tree, GET_LENGTH(GET_LEVEL(L Aux_level)), (CONS Aux_level (GET_LEVEL(L Aux_level)))), if GET_LENGTH(GET_LEVEL(L Aux_level))>Max_nodes
;   =  GET_MAX_LEVEL(L, Aux_level+1, Height_tree, Max_nodes, List_max_nodes )

(DEFUN GET_MAX_LEVEL (L Aux_level Height_tree Max_nodes List_max_nodes)
  (COND 
   ((EQL Aux_level Height_tree) List_max_nodes)
   ((> (SETQ Max_level_nodes (GET_LENGTH (SETQ Nodes (GET_LEVEL L Aux_level)) 0)) Max_nodes) (GET_MAX_LEVEL L (+ 1 Aux_level) Height_tree Max_level_nodes (CONS Aux_level Nodes)))
   (T (GET_MAX_LEVEL L (+ 1 Aux_level) Height_tree Max_nodes List_max_nodes))
  )
)




;main function for the get max level
(DEFUN MAIN_MAX_LEVEL (L)
  (SETQ Height_tree (HEIGHT L 0))
  (SETQ NODES (GET_MAX_LEVEL L 0 Height_tree 0 ()))
  (PRINT (CAR NODES))
  (PRINT (CDR NODES))

)


