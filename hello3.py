import time
from pyspark.sql import SparkSession
from pyspark.sql import functions as f

warehouse_location = "abspath('/tmp/warehouse/test/')"
spark = SparkSession.builder.appName("bad_files")\
    .config("spark.sql.warehouse.dir", warehouse_location)\
    .config("spark.hadoop.orc.overwrite.output.file", "true")\
    .enableHiveSupport()\
    .getOrCreate()
spark.sparkContext.setLogLevel("ERROR")

"""
dad_df = spark.sql("select serial_num, event_date, trans_seq from dim.drive_attr_dim dad where dad.hadoop_etl_load_date > '2021-06-07 01:45:40' and dad.hadoop_etl_load_date <= '2021-06-08 07:12:35' and dad.event_date_key in (20210604, 20210527, 20210508, 20210607, 20210603, 20210608, 20201110, 20210606, 20210605)")
dad_df.createOrReplaceTempView("drive_attr_delta")

def_backfill = spark.sql("select drive_serial_num,drive_event_fact.trans_seq,drive_event_fact.event_date, date_key  from drive.drive_event_fact inner join drive_attr_delta on (drive_attr_delta.serial_num=drive_event_fact.drive_serial_num and (drive_event_fact.event_date > drive_attr_delta.event_date or (drive_event_fact.event_date = drive_attr_delta.event_date and drive_event_fact.trans_seq >= drive_attr_delta.trans_seq))) where date_key between date_format(date_sub(to_timestamp('2021-06-07 01:45:40'),15),'yyyyMMdd') and date_format(date_add(to_timestamp('2021-06-07 01:45:40'),1),'yyyyMMdd') and event_status in ('P', 'F' ,'PASS', 'FAIL') and drive_event_fact.hadoop_etl_load_date <= '2021-06-07 01:45:40'")

def_backfill.explain()

union_partitions = def_backfill.select(f.col("date_key")).distinct().rdd.flatMap(lambda x: x).collect()
print(union_partitions)
date_key=20201204 and insert_version=2012210729 and date_key is not null and insert_version is not null
"""
df = spark.read.format("orc").option("compression", "zlib").load("s3://stx-usw2-ehc-prd-data-t2/dim_flat.db_drive_event_attr_flat/date_key=20201204/insert_version=2012210729/part-00001-5e22a873-82a5-4781-9eb9-473b483396bd.c000.zlib.orc")
df.createOrReplaceTempView("drive_event_attr_flat")
df2 = spark.sql("select a.* from drive_event_attr_flat a left join drive_event_attr_flat b on (a.drive_serial_num=b.drive_serial_num and a.event_date >= b.event_date)")
df2.write.format("orc").option("compression", "zlib").mode("Append").save("s3a://stx-usw2-ehc-prd-staging-2/spark-k8s-test2")

spark.stop()

