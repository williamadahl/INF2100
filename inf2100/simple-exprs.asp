def make_add_n (n):
   def f (v):
      return v + n
   return f
add1 = make_add_n(1)
add5 = make_add_n(5)
add1(2)
add5(2)
