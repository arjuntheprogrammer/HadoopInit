import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mrunit.MapDriver;

import junit.framework.TestCase;

public class TextExample extends TestCase {
	public static class maptest extends Mapper<LongWritable, Text, Text, IntWritable>{
		Text day = new Text();
		
		//word-count logic 
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			
			String[] line = value.toString().split(",");
			int val = Integer.parseInt(line[0]);
			day.set(line[1]);
			context.write(day, new IntWritable(val));
				
		}
	}
	
	MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
	
	public void setUp() {
		new maptest();
		mapDriver = MapDriver.newMapDriver();
		
	}
	
	@Test
	public void testMapper() {
		try {
			mapDriver.withInput(new LongWritable(), new Text("1, sunday, awanish, holiday") )
					 .withOutput(new Text("sunday"), new IntWritable(2))
					 .runTest();
			
		}
	}
	
	
	
	
}
