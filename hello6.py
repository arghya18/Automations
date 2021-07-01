import time
import pymysql
from pyspark.sql import SparkSession
import sys
import json

spark = SparkSession.builder.appName("bad_files").getOrCreate()
spark.sparkContext.setLogLevel("INFO")

print(sys.argv[1])
args = json.loads(sys.argv[1])
print(args)

df = spark.range(10)
df.show()

df1 = spark.read.format("orc").option("compression", "zlib").load("s3a://stx-usw2-ehc-prd-data-t1/dim.db_media_sbr_lkp/20210607215800-20210608215700")
df1.show(100)
time.sleep(300)

spark.stop()
