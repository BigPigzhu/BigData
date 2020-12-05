# 字典，相当于java中的hashmap


m = {"001": "张三", "002": "李四"}

# 通过key获取value

print(m["001"])

# 增加新的元素

m["003"] = "王五"

# 修改value
m["003"] = "王五1"
print(m)

# 删除元素
del m["002"]

print(m)

# 判断key是否再字典中存在
print("001" in m)

print(m.keys())
print(m.values())

# 字典的遍历

for k, v in m.items():
    print(k, v)
