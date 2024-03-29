
# Finn alle primtall opp til n
# ved hjelp av teknikken kalt «Eratosthenes' sil».

n = 1000
primes = [True] * (n+1)

def find_primes():
   i1 = 2
   while i1 <= n:
      i2 = 2 * i1
      while i2 <= n:
         primes[i2] = False
         i2 = i2 + i1
      i1 = i1+1

def w4(n):
    if n <= 9:
        return '   ' + str(n)
    elif n <= 99:
        return '  ' + str(n)
    elif n <= 999:
        return ' ' + str(n)
    else:
        return str(n)
   

def list_primes():
    n_printed = 0
    line_buf = ''
    i = 2
    while i <= n:
       if primes[i]:
          if n_printed > 0 and n_printed % 10 == 0:
             print(line_buf)
             line_buf = ''
          line_buf = line_buf + w4(i)
          n_printed = n_printed + 1
       i = i+1
    print(line_buf)


list_primes()
