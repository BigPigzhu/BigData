import requests
import json

url = "https://club.jd.com/comment/productPageComments.action?productId=100006607659&score=0&sortType=5&page=0&pageSize=10&isShadowSku=0&fold=1"

# 请求头
headers = {
    "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36"
}

response = requests.get(url=url, headers=headers)

loads = json.loads(response.text)


for comment in loads["comments"]:
    print(comment["content"])
