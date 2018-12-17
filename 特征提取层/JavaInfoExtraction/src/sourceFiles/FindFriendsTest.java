package sourceFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;


public class FindFriendsTest {
	MapDriver<LongWritable, Text, Text, Text> mapDriver;
	ReduceDriver<Text, Text, Text, Text> reduceDriver;
	MapReduceDriver<LongWritable, Text, Text, Text, Text, Text> mapReduceDriver;
	
	@Before
	public void setUp() {
		FindFriends.FriendMapper mapper = new FindFriends.FriendMapper();
		FindFriends.FriendReduce reducer = new FindFriends.FriendReduce();
		mapDriver = MapDriver.newMapDriver(mapper);
		reduceDriver = ReduceDriver.newReduceDriver(reducer);
		mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
	}
	
	@Test
	public void testMapper() throws IOException {
		String t = "0\t1,2,3";
		mapDriver.withInput(new LongWritable(1), new Text(t))
				.withOutput(new Text("1"), new Text("2-0"))
				.withOutput(new Text("2"), new Text("1-0"))
				.withOutput(new Text("1"), new Text("3-0"))
		        .withOutput(new Text("3"), new Text("1-0"))
		        .withOutput(new Text("2"), new Text("3-0"))
				.withOutput(new Text("3"), new Text("2-0"))
				.withOutput(new Text("0"), new Text("1-*"))
				.withOutput(new Text("0"), new Text("2-*"))
				.withOutput(new Text("0"), new Text("3-*"));
		
		mapDriver.runTest();
	}
	
	
	@Test
	public void testReducer() throws IOException {
		/**
		 * 假设输入为：
		 * 0,
		 * 1-*,2-*,3-*
		 * 4-1,5-1
		 * 3-2,4-2
		 * 2-3
		 */
		List<Text> values = new ArrayList<Text>();
		values.add(new Text("1-*"));
		values.add(new Text("2-*"));
		values.add(new Text("3-*"));
		values.add(new Text("4-1"));
		values.add(new Text("5-1"));
		values.add(new Text("3-2"));
		values.add(new Text("4-2"));
		values.add(new Text("2-3"));
		reduceDriver.withInput(new Text("0"), values).withOutput(new Text("0"), new Text("4, 5, "));
		reduceDriver.runTest();
	}
	
	
	/**
	 * 期望结果：
	 * 0 4 5
	 * 1 2 3
	 * 2 1
	 * 3 1 4
	 * 4 0 3 5
	 * 5 0 4
	 * @throws IOException
	 */
	@Test
	public void testMapReducer() throws IOException {
		List<Pair<LongWritable, Text>> list = new ArrayList<>();
		Text text1 = new Text("0\t1,2,3");
		Text text2 = new Text("1\t0,4,5");
		Text text3 = new Text("2\t0,3,4");
		Text text4 = new Text("3\t0,2");
		Text text5 = new Text("4\t1,2");
		list.add(new Pair<LongWritable,Text>(new LongWritable(), text1));
		list.add(new Pair<LongWritable,Text>(new LongWritable(), text2));
		list.add(new Pair<LongWritable,Text>(new LongWritable(), text3));
		list.add(new Pair<LongWritable,Text>(new LongWritable(), text4));
		list.add(new Pair<LongWritable,Text>(new LongWritable(), text5));
		mapReduceDriver.withAll(list)
					.withOutput(new Text("0"), new Text("4, 5, "))
					.withOutput(new Text("1"), new Text("2, 3, "))
					.withOutput(new Text("2"), new Text("1, "))
					.withOutput(new Text("3"), new Text("1, 4, "))
					.withOutput(new Text("4"), new Text("0, 3, 5, "))
					.withOutput(new Text("5"), new Text("0, 4, "))
					.runTest();
	}
	
}
