import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;

import junit.framework.TestCase;

public class TextExample extends TestCase {
	public static class maptest extends Mapper<LongWritable, Text, Text, IntWritable>{
		Text day = new Text();
		
		//word-count logic 
		@Override
		protected void map(LongWritable key, Text value, Context ct)
				throws IOException, InterruptedException {
			
			String[] line = value.toString().split(",");
			int val = Integer.parseInt(line[0]);
			day.set(line[1]);
			ct.write(day, new IntWritable(val));
				
		}
	}
	
	MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
	
	public void setUp() {
		maptest mp = new maptest();
		mapDriver = MapDriver.newMapDriver(mp);	
	}
	
	@Test
	public void testMapper() {
		try {
			mapDriver.withInput(new LongWritable(), new Text("1,sunday,awanish,holiday") )
					 .withOutput(new Text("sunday"), new IntWritable(1))
					 .runTest();
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}	
}
