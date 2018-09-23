import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class MyMaxMin {
	public static class MaxMinTemperatureMapper extends Mapper<LongWritable, Text, Text, Text>{
		public static final int Missing = 9999;
		
		/*
		 Field#  Name                           Units
		---------------------------------------------
		   1    WBANNO                         XXXXX
		   2    LST_DATE                       YYYYMMDD
		   3    CRX_VN                         XXXXXX
		   4    LONGITUDE                      Decimal_degrees
		   5    LATITUDE                       Decimal_degrees
		   *6    T_DAILY_MAX                    Celsius
		   *7    T_DAILY_MIN                    Celsius
		   8    T_DAILY_MEAN                   Celsius
		   9    T_DAILY_AVG                    Celsius
		   10   P_DAILY_CALC                   mm
		   11   SOLARAD_DAILY                  MJ/m^2
		   12   SUR_TEMP_DAILY_TYPE            X
		   13   SUR_TEMP_DAILY_MAX             Celsius
		   14   SUR_TEMP_DAILY_MIN             Celsius
		   15   SUR_TEMP_DAILY_AVG             Celsius
		   16   RH_DAILY_MAX                   %
		   17   RH_DAILY_MIN                   %
		   18   RH_DAILY_AVG                   %
		   19   SOIL_MOISTURE_5_DAILY          m^3/m^3
		   20   SOIL_MOISTURE_10_DAILY         m^3/m^3
		   21   SOIL_MOISTURE_20_DAILY         m^3/m^3
		   22   SOIL_MOISTURE_50_DAILY         m^3/m^3
		   23   SOIL_MOISTURE_100_DAILY        m^3/m^3
		   24   SOIL_TEMP_5_DAILY              Celsius
		   25   SOIL_TEMP_10_DAILY             Celsius
		   26   SOIL_TEMP_20_DAILY             Celsius
		   27   SOIL_TEMP_50_DAILY             Celsius
		   28   SOIL_TEMP_100_DAILY            Celsius

		 */
		//Input = 26565 20160101 -9.000 -148.46   70.16 -9999.0 -9999.0 -9999.0 -9999.0 -9999.0 -9999.00 U -9999.0 -9999.0 -9999.0 -9999.0 -9999.0 -9999.0 -99.000 -99.000 -99.000 -99.000 -99.000 -9999.0 -9999.0 -9999.0 -9999.0 -9999.0
		//
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
				
			String line = value.toString();
			if(!(line.length() == 0)) {
				
				//fetching date
				String date = line.substring(6,  14);
				
				//fetching max temperature
				float temp_max = Float.parseFloat(line.substring(39, 45).trim());
				
				//fetching minimum temperature
				float temp_min = Float.parseFloat(line.substring(47, 54).trim());
				
				if(temp_max > 35.0 && temp_max != Missing) {
					context.write(new Text("Hot Day "+ date), new Text(String.valueOf(temp_max)));
				}
				
				if(temp_max < 10.0 && temp_min != Missing) {
					context.write(new Text("Cold Day "+ date), new Text(String.valueOf(temp_min)));
				}				
			}			
		}	
	}
	
	public static class MaxMinTemperatureReducer extends Reducer<Text, Text, Text, Text>{
		@Override
		protected void reduce(Text arg0, Iterable<Text> arg1, Reducer<Text, Text, Text, Text>.Context arg2)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.reduce(arg0, arg1, arg2);
		}
	}
	
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "weather example");
		job.setJarByClass(MyMaxMin.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setMapperClass(MaxMinTemperatureMapper.class);
		job.setReducerClass(MaxMinTemperatureReducer.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		Path OutputPath = new Path(args[1]);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job,  new Path(args[1]));
		
		OutputPath.getFileSystem(conf).delete(OutputPath, true);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
		
		
	}
}





