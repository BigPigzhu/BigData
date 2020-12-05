from pyspark import SparkConf, SparkContext

conf = SparkConf().setAppName("wc").setMaster("local")

sc = SparkContext(conf=conf)

sc.textFile("words.txt") \
    .flatMap(lambda line: line.split(",")) \
    .map(lambda word: (word, 1)) \
    .reduceByKey(lambda x, y: x + y) \
    .saveAsTextFile("wc")
