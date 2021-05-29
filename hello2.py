import time
from pyspark.sql import SparkSession

warehouse_location = "abspath('/tmp/warehouse/test/')"
spark = SparkSession.builder.appName("bad_files")\
    .config("spark.sql.warehouse.dir", warehouse_location)\
    .config("spark.hadoop.orc.overwrite.output.file", "true")\
    .enableHiveSupport()\
    .getOrCreate()
spark.sparkContext.setLogLevel("ERROR")

path = 's3://stx-usw2-ehc-prd-staging-2/spark-k8s-data'

df = spark.range(10)
df.show()

print('Sleeping for debug')
time.sleep(3)

#df2 = spark.read.format("orc").option("compression", "zlib").load("s3://stx-usw2-ehc-prd-data-t2/test.db_p135_final_contact_fact/event_date_key=20210510")
df2 = spark.sql('select * from drive.p135_final_contact_fact where event_date_key in (20210510, 20210511, 20210513)')
df2.write.partitionBy("event_date_key", "atlas_insert_ver").format("orc").option("compression", "zlib").mode('append').save(path)
spark.stop()
