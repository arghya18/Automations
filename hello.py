from pyspark.sql import SparkSession

warehouse_location = "abspath('/tmp/warehouse/test/')"
spark = SparkSession.builder.appName("bad_files")\
    .config("spark.sql.warehouse.dir", warehouse_location)\
    .config("spark.hadoop.orc.overwrite.output.file", "true")\
    .enableHiveSupport()\
    .getOrCreate()
spark.sparkContext.setLogLevel("INFO")

df = spark.range(10)
df.show()

path = 's3a://stx-usw2-ehc-prd-staging-2/spark-k8s'
df.write.format('orc').mode('append').save(path)

df2 = spark.sql('select * from tmp.test_date_dim limit 100')
df2.show()
df2.write.format('orc').mode('append').save(path)
