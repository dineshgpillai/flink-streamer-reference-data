/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.dineshgpillai;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.util.OutputTag;
import org.fpml.legal.LegalDocument;
import org.fpml.legal.Party;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="http://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingJob {

	final static Logger LOG = LoggerFactory.getLogger(StreamingJob.class);

	public static void main(String[] args) throws Exception {
		// set up the streaming execution environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		/*
		 * Here, you can start creating your execution plan for Flink.
		 *
		 * Start with getting some data from the environment, like
		 * 	env.readTextFile(textPath);
		 *
		 * then, transform the resulting DataStream<String> using operations
		 * like
		 * 	.filter()
		 * 	.flatMap()
		 * 	.join()
		 * 	.coGroup()
		 *
		 * and many more.
		 * Have a look at the programming guide for the Java API:
		 *
		 * http://flink.apache.org/docs/latest/apis/streaming/index.html
		 *
		 */

		// execute program
		LOG.info("starting up Streaming job...");
		DataStream<String> dataStream = env.fromElements(args[0]);
		StreamingJob job = new StreamingJob();
		job.streamAndSink(dataStream, new LegalDocumentSink(), new PartySink());
		env.execute("Flink Streaming Java API Skeleton");
		assert LegalDocumentSink.values.size() > 0;
		assert PartySink.values.size() > 0;

		LOG.info("Done streaming");
	}


	public void streamAndSink(DataStream<String> stream, SinkFunction<LegalDocument> legalDocumentSink, SinkFunction<List<Party>> partySink) {
		SingleOutputStreamOperator<LegalDocument> mainDataStream = stream.process(new LegalDocumentTransformer());
		mainDataStream.addSink(legalDocumentSink);

		final OutputTag<List<Party>> outputTag = new OutputTag<List<Party>>("party-stream") {
		};

		DataStream<List<Party>> partyStream = mainDataStream.getSideOutput(outputTag);
		partyStream.addSink(partySink);

	}
}