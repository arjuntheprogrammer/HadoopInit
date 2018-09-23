import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import javafx.scene.text.Text;

public class UniqueListeners {
	
	public class LastFmConstants{
		public static final int user_Id = 0;
		public static final int track_Id = 1;
		public static final int shard = 2;
		public static final int radio = 3;
		public static final int skipped = 4;
	}
	
	//Input - 111129|208|1|1|0 - userId|trackId|shared|radio|skipped
	
	public static class UniqueListenerMapper extends Mapper<Object, Text, IntWritable, IntWritable>{
		
		IntWritable trackId = new IntWritable();
		IntWritable userId = new IntWritable();
		
		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, IntWritable, IntWritable>.Context context)
				throws IOException, InterruptedException {
			
			String[] parts = value.toString().split("[|]");
			trackId.set(Integer.parseInt(parts[LastFmConstants.track_Id]));
			userId.set(Integer.parseInt(parts[LastFmConstants.user_Id]));
			
			context.write(trackId, userId);
		}

	}
	
	public static class UniqueListenerReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable>{
		@Override
		protected void reduce(IntWritable trackId, Iterable<IntWritable> userIds,
				Reducer<IntWritable, IntWritable, IntWritable, IntWritable>.Context context)
				throws IOException, InterruptedException {
				
			Set<Integer> userIdSet = new HashSet<Integer>();
			
			for (IntWritable userId: userIds) {
				userIdSet.add(userId.get());
			}
			IntWritable size = new IntWritable(userIdSet.size());
			context.write(trackId, size);			
		}
		
	}
	
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "weather example");
		job.setJarByClass(UniqueListeners.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setMapperClass(UniqueListenerMapper.class);
		job.setReducerClass(UniqueListenerReducer.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		Path OutputPath = new Path(args[1]);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job,  new Path(args[1]));
		
		OutputPath.getFileSystem(conf).delete(OutputPath, true);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
	}

}
