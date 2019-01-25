package io.github.dineshgpillai;

import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.fpml.legal.LegalDocument;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class LegalDocumentTransformer extends ProcessFunction<String, LegalDocument> {

    final OutputTag<LegalDocument> outputTag = new OutputTag<LegalDocument>("party-stream") {
    };

    @Override
    public void processElement(String s, ProcessFunction<String, LegalDocument>.Context ctx, Collector<LegalDocument> collector) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(LegalDocument.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        LegalDocument ld= (LegalDocument) jaxbUnmarshaller.unmarshal(targetStream);
        collector.collect(ld);
        ctx.output(outputTag, ld);

    }
}
