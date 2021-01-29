from pyspark.sql import SparkSession

warehouse_location = "abspath('/tmp/warehouse/test/')"
spark = SparkSession.builder.appName("bad_files")\
    .config("spark.sql.warehouse.dir", warehouse_location)\
    .config("spark.hadoop.orc.overwrite.output.file", "true")\
    .getOrCreate()
spark.sparkContext.setLogLevel("ERROR")

df = spark.sparkContext.parallelize([1, 2, 3])
df.show()
