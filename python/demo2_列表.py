# 通过[] 定义一个列表
# 相当于java中的Arraylist

l = [1, 2, 3, 4, 5, 6, 7, 8]

print(l)

# 增加元素
l.append(100)

print(l)

# 删除指定下标的元素
del l[2]

print(l)

# 获取对应位置的元素
print(l[1])

# 插入元素
l.insert(3, 20)

print(l)

# 负下标   -1  到 -N
print(l[-1])

# 获取容器的长度
print(len(l))

# 列表的切片

l = [1, 2, 3, 4, 5, 6, 7, 8, 9]

print(l[3:6:1])

# 索引位置不写代表从头到尾
print(l[::2])

# 反转列表
# 当步长为负数的时候从后面往前面截取
print(l[::-1])

# 嵌套列表
ll = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]

print(ll[1][1])


