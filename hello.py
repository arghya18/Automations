import time
import pymysql
from pyspark.sql import SparkSession

warehouse_location = "abspath('/tmp/warehouse/test/')"
spark = SparkSession.builder.appName("bad_files")\
    .config("spark.sql.warehouse.dir", warehouse_location)\
    .config("spark.hadoop.orc.overwrite.output.file", "true")\
    .enableHiveSupport()\
    .getOrCreate()
spark.sparkContext.setLogLevel("ERROR")

path = 's3a://stx-usw2-ehc-prd-staging-2/spark-k8s-data'

df = spark.range(10)
df.show()

df2 = spark.sql('select count(*) from dim.drive_attr_dim limit 100')
df2.show()
df2.write.format('orc').mode('append').save(path)

print('Sleeping for debug')
time.sleep(3)

df.write.format('orc').mode('append').save(path)
