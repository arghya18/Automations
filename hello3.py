import time
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

df2 = spark.read.format('orc').option('compression': 'zlib').load('s3://stx-usw2-ehc-prd-data-t2/dim.db_date_dim/atlas_insert_ver=2102050716')
df.write.format('orc').mode('append').save(path)


print('Sleeping for debug')
time.sleep(300)

df.write.format('orc').mode('append').save(path)
