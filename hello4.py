import time
import pymysql
from pyspark.sql import SparkSession
import sys
import json

warehouse_location = "abspath('/tmp/warehouse/test/')"
spark = SparkSession.builder.appName("bad_files")\
    .config("spark.sql.warehouse.dir", warehouse_location)\
    .config("spark.hadoop.orc.overwrite.output.file", "true")\
    .enableHiveSupport()\
    .getOrCreate()
spark.sparkContext.setLogLevel("INFO")

print(sys.argv[1])
args = json.loads(sys.argv[1])
print(args)

df = spark.range(10)
df.show()

df2 = spark.sql('describe lyve_lab.date_dim')
df2.show()
print('Sleeping for debug')
time.sleep(300)

spark.stop()
