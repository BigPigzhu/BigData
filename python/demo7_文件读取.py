"""
mode : r ： 读 ，w: 写， a : 追加

"""

file_read = open("students.txt", encoding="utf-8", mode="r")

# 读取所有行
lines = file_read.readlines()

# 去掉前后的空格
lines = [line.strip() for line in lines]

print(lines)

# 回收资源
file_read.close()

# 保存数据到文件
w_file = open("test.txt", mode="a", encoding="utf-8")

w_file.write("python")
w_file.write("\n")
w_file.write("中文")

# with  方法会自动关闭文件
with open("students.txt", encoding="utf-8") as f:
    print(f.readlines())
