from pyspark.sql import SparkSession

warehouse_location = "abspath('/tmp/warehouse/test/')"
spark = SparkSession.builder.appName("bad_files")\
    .config("spark.sql.warehouse.dir", warehouse_location)\
    .config("spark.hadoop.orc.overwrite.output.file", "true")\
    .getOrCreate()
spark.sparkContext.setLogLevel("DEBUG")

df = spark.range(10)
df.show()

path = 's3://stx-usw2-ehc-prd-staging-2/spark-k8s'
df.write.format('orc').mode('append').save(path)
