package io.github.dineshgpillai;

import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.fpml.legal.Party;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PartySink extends RichSinkFunction<List<Party>> {

    final static Logger LOG = LoggerFactory.getLogger(PartySink.class);
    public static final List<Party> values = new ArrayList<>();

    @Override
    public synchronized void invoke(List<Party> value) throws Exception {
        value.forEach(a-> values.add(a));

    }

}
