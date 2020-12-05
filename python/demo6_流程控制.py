age = 23

if age >= 18:
    print("成年")
else:
    print("未成年")

l = [1, 2, 3, 4, 5, 6, 7, 8, 9]

for i in l:
    print(i)

i = 0
sum = 1

while i <= 10:
    i += 1
    sum = sum * i

print(sum)

l = [1, 2, 3, 4, 5, 6, 7, 8, 9]
new_list = []
for i in l:
    new_list.append(i * 2)

print(new_list)

# 列表推导式

l2 = [i * 2 for i in l]
print(l2)
