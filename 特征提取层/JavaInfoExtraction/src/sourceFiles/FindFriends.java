package sourceFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.jasper.tagplugins.jstl.core.If;

public class FindFriends {

	
	static class FriendMapper extends Mapper<LongWritable, Text, Text, Text> {
		
		/**
		 * input: 一行数据
		 * output: <0 , 117-2>, <117, 0-2> 最后一个数字2代表共同好友ID
		 *    还需携带好友的信息<0 ,1-*>
		 */
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] split = line.split("\t");
			if (split.length != 2) {
                return;
            }
			String friend = split[0];
			String[] persons = split[1].split(",");
			Arrays.sort(persons);
			
			for(int i = 0 ; i < persons.length-1; i ++){
	            for(int j = i+1 ; j < persons.length; j ++){
	                context.write(new Text(persons[i]), new Text(persons[j] + "-" + friend));
	                context.write(new Text(persons[j]), new Text(persons[i] + "-" + friend));
	            }
	        }
			
			for (int i = 0; i < persons.length; i++) {
				context.write(new Text(friend), new Text(persons[i] + "-*"));
			}
		}
		
	}
	// <0,117-2> <0,1220-2> <117,1220-2> <117, 12453-2>
	
	static class FriendReduce extends Reducer<Text,Text,Text,Text>{
		
		/**
		 *  input, like: <0 , 117-2>、<0 , 1120-0> <0 , 12-*>  搜有的0开头的key值
		 *  output, like:<0 , 117,11,22>
		 */
        @Override
        protected void reduce(Text person_person, Iterable<Text> friends, Context context) 
        		throws IOException, InterruptedException {
            Map<String, Integer> map = new HashMap<>(); // <非好友id,共同好友个数>
            ArrayList<String> friendList = new ArrayList<>(); 
            ArrayList<String> dataList = new ArrayList<>(); //先保存非 12-* 这种类型的数据
            
            // 找到好友
            Iterator<Text> it = friends.iterator();
            String spilt1;
            String spilt2;
            while (it.hasNext()) {
            	String temp = it.next().toString();
            	spilt1 = temp.split("-")[0];
            	spilt2 = temp.split("-")[1];
            	if (spilt2.equals("*"))
            		friendList.add(spilt1);
            	else
            		dataList.add(temp);
            }
            
            // 推荐好友
            for (String temp : dataList) {
            	spilt1 = temp.split("-")[0];
            	if (!friendList.contains(spilt1)) {
            		// 加入map
            		if (map.containsKey(spilt1)) {
            			// 有这个key，只需要数量加一
            			int value = map.get(spilt1).intValue() + 1;
            			map.replace(spilt1, value);
            		} else {
            			// 没有这个key
            			map.put(spilt1, 1);
            		}
            	}
            }
            
            // 将map按value排序，取前10个好友输出
            // 这里将map.entrySet()转换成list
            List<Map.Entry<String, Integer>> li = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
            Collections.sort(li,new Comparator<Map.Entry<String, Integer>>() {

				@Override
				public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
					// 降序排列
					return o2.getValue().compareTo(o1.getValue());
				}
            	
			});
            
            // 最多取10个数
            int time = (li.size() > 10) ? 10 : li.size();
            Map.Entry<String, Integer> item;
            
            StringBuffer sb = new StringBuffer(); // 保存取的Sting值
            for (int i = 0; i < time; i++) {
            	item = li.get(i);
            	sb.append(item.getKey()).append(", ");
            }
            context.write(person_person, new Text(sb.toString()));
            
        }
    }
	
	public static void main(String[] args) 
		throws IOException, ClassNotFoundException, InterruptedException {
		
		String input = "D:\\mr\\qq\\out1";
        String output = "D:\\mr\\qq\\out2";
        
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(FindFriends.class);

        job.setMapperClass(FriendMapper.class);
        job.setReducerClass(FriendReduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job,new Path(input));
        FileOutputFormat.setOutputPath(job,new Path(output));

        boolean b = job.waitForCompletion(true);
        if(b){}
	}
}
