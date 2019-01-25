package io.github.dineshgpillai;

import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.fpml.legal.LegalDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class LegalDocumentSink extends RichSinkFunction<LegalDocument> {

    final static Logger LOG = LoggerFactory.getLogger(LegalDocumentSink.class);
    public static final List<LegalDocument> values = new ArrayList<>();

    @Override
    public synchronized void invoke(LegalDocument value) throws Exception {
        values.add(value);
    }
}
