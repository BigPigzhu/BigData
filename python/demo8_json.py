read = open("students.json", encoding="utf-8")

lines = read.readlines()

lines = [line.strip() for line in lines]

# 官方自带处理json数据的工具
import json

for line in lines:
    # 将字符串转成json的对象
    json_obj = json.loads(line)

    name = json_obj["name"]
    age = json_obj["age"]

    print(name, age, type(name), type(age))
