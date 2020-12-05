import pandas as pd
import matplotlib.pyplot as plot

series = pd.Series(data=["java", "spark", "python"], index=["a", "b", "c"])

print(series)
print(type(series))

print(series["a"])

m = {
    "name": ["张三", "李四", "王五"],
    "age": [23, 24, 25]
}

# DataFrame 有行有列的表结构

frame = pd.DataFrame(data=m)

print(frame)

print(frame[frame["age"] > 24])

print("=" * 100)
# 读取文件

students = pd.read_csv("students.txt", encoding="utf-8", header=None, names=["id", "name", "age", "gender", "clazz"])

print(students)
print("=" * 100)

print(students[students["gender"] == "男"])

students["clazz"].value_counts().plot.bar()

# 展示效果
# plot.show()


scores = pd.read_csv("score.txt", encoding="utf-8", header=None, names=["stu_id", "cou_id", "score"])

print(scores.groupby(by="stu_id").sum("score").reset_index())

# join


merge = pd.merge(students, scores, how='inner', left_on="id", right_on="stu_id")

sum_score = merge.groupby(by="name")["score"].sum().reset_index()

# 保存数据
sum_score.to_csv("sum_score.txt", encoding="utf-8", header=None, index=None)

print(students[["id", "name"]])

print(students.describe())

print(students.head())
