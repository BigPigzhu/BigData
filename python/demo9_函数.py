def square(x):
    s = x * 2
    return s


sum = square(10)

print(sum)

# lambea 函数

l = lambda x, y: x + y

print(l(10, 20))


# 高阶函数
# 以函数作为参数

def f1(f):
    # 调用传入进来的函数
    s = f(10, 20)
    print(s)


f1(lambda x, y: x * y)
