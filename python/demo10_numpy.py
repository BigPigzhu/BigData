import numpy as np

# 矩阵
array = np.array([[1, 2, 3], [4, 5, 6]])

print(array)
print(type(array))

print(array[1])

# 修改元素
array[1] = 100

print(array)

# 运算
print(array * 2)
print(array / 2)
print(array + 2)
print(array - 2)
print(array // 2)
print(array ** 2)

array = np.array([[1, 2, 3], [4, 5, 6]])

print(array)

print(array[::, ::2])

print(array[::-1, ::-1])

print(array >= 2)

# 布尔值索引
print(array[array >= 2])

# 重构
print(array.reshape(3, 2))

array1 = np.array([[1, 2, 3], [4, 5, 6]])
array2 = np.array([[1, 2, 3], [4, 5, 6]])

# 拼接
print(np.vstack((array1, array2)))
print("=" * 100)
print(np.hstack((array1, array2)))

array = np.array([[1, 2, 3], [4, 5, 6]])

print(array)

# 转置
print(array.T)

# 左右翻转
print(np.fliplr(array))

# 上下翻转
print(np.flipud(array))

# 对位运算
array1 = np.array([[1, 2, 3], [4, 5, 6]])
array2 = np.array([[10, 20, 30], [40, 50, 60]])

print(array1 + array2)
print(array1 - array2)
print(array1 / array2)
print(array1 * array2)
print(array1 // array2)
print(array1 % array2)
print(array1 ** array2)

# 矩阵相乘

array1 = np.array([[1, 2, 3], [4, 5, 6]])
array2 = np.array([[1, 2, 3], [4, 5, 6]]).T

print(array1)
print(array2)

print(array1.dot(array2))

array = np.array([1, 2, 3, 4, 5, 6, 7, 8, 30])
print(np.sin(array))

array = np.array([1, 2, 3, 4, 5, 6, 7, 8, 30])

print(np.diff(array))

array = np.array([[1, 2, 3], [4, 5, 6]])

print(np.diff(array, axis=0))

print(np.std(array, axis=0))
