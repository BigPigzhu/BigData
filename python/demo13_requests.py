import requests
import json

get = requests.get("https://m.weibo.cn/comments/hotflow?id=4579396801268479&mid=4579396801268479&max_id_type=0")

json_loads = json.loads(get.text)

for comment in json_loads["data"]["data"]:
    print(comment["user"]["screen_name"], comment["text"])
